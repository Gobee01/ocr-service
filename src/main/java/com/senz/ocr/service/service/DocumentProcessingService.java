package com.senz.ocr.service.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.senz.ocr.service.api.WebSocketApi;
import com.senz.ocr.service.data.dto.ClassificationDTO;
import com.senz.ocr.service.data.dto.ProcessPdfResponseDTO;
import com.senz.ocr.service.data.entity.Classification;
import com.senz.ocr.service.data.entity.Extraction;
import com.senz.ocr.service.data.entity.UploadedDocument;
import com.senz.ocr.service.data.repository.ClassificationRepository;
import com.senz.ocr.service.data.repository.ExtractionRepository;
import com.senz.ocr.service.data.repository.UploadedDocumentRepository;
import com.senz.ocr.service.data.support.ExtractionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class DocumentProcessingService {

    @Value("${process.pdf.url}")
    private String processPdfUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentProcessingService.class);

    @Autowired
    ExtractionRepository extractionRepository;

    @Autowired
    ClassificationRepository classificationRepository;

    @Autowired
    UploadedDocumentRepository uploadedDocumentRepository;

    @Autowired
    private WebSocketApi webSocketApi;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public void processDocument(String pdfFilePath, Integer documentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String extractedPath = extractFilePath(pdfFilePath);
        String fullPath = "/app/gen_OCR/" + extractedPath;

        String requestBody = String.format("{\"pdf_file_path\": \"%s\", \"_id\": \"%s\"}", fullPath, documentId.toString());
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        LOGGER.debug("Starting extraction for Document: " + documentId);
        UploadedDocument document = uploadedDocumentRepository.findByDocumentId(documentId);
        try {
            ResponseEntity<String> response = restTemplate.exchange(processPdfUrl, HttpMethod.POST, entity, String.class);

            // Handle response and update status accordingly
            if (response.getStatusCode().is2xxSuccessful()) {
                LOGGER.debug("Got Response Successfully for document: " + documentId);
                // Configure ObjectMapper to handle NaN
                ObjectMapper objectMapper = JsonMapper.builder()
                        .enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)
                        .build();
                ProcessPdfResponseDTO responseDTO = objectMapper.readValue(response.getBody(), ProcessPdfResponseDTO.class);

                saveClassificationData(responseDTO.getClassification(), documentId);
                saveExtractionData(responseDTO.getKey_vlaue_information(), responseDTO.getTable_extraction(), documentId);
                document.setStatus(ExtractionStatus.VALIDATION_PENDING);
            } else {
                LOGGER.debug("Failed Response:" + response);
                document.setStatus(ExtractionStatus.EXTRACTION_FAILED);
            }
        } catch (ResourceAccessException e) {
            // Handle timeout
            LOGGER.error("Timeout occurred while processing document: " + documentId, e);
            document.setStatus(ExtractionStatus.EXTRACTION_FAILED);
        } catch (Exception e) {
            LOGGER.error("Error processing document: " + documentId, e);
            document.setStatus(ExtractionStatus.EXTRACTION_FAILED);
        }

        uploadedDocumentRepository.save(document);
        webSocketApi.notifyExtractionComplete(document);
        // Process the next document in the queue
        processNextInQueue();
    }

    private void saveClassificationData(Map<String, Map<String, String>> classificationData, Integer documentId) {
        Classification classification = new Classification();
        classification.setDocumentId(documentId);
        classification.setClassification(classificationData);
        classificationRepository.save(classification);
    }

    private void saveExtractionData(Map<String, List<List<String>>> keyValues, Map<String, Map<String, List<Map<String, Object>>>> tableExtraction, Integer documentId) {
        Extraction extraction = new Extraction();
        extraction.setDocumentId(documentId);
        extraction.setKeyValues(keyValues);
        extraction.setTableExtraction(tableExtraction);
        extractionRepository.save(extraction);
    }

    private String extractFilePath(String fullPath) {
        int startIndex = fullPath.indexOf("data/");
        if (startIndex != -1) {
            return fullPath.substring(startIndex);
        }
        return fullPath;
    }

    private void processNextInQueue() {
        UploadedDocument nextDocument = uploadedDocumentRepository.findFirstByStatusOrderByIdAsc(ExtractionStatus.IN_QUEUE);
        if (nextDocument != null) {
            // Start processing the next document
            nextDocument.setStatus(ExtractionStatus.EXTRACTION_IN_PROGRESS);
            uploadedDocumentRepository.save(nextDocument);
            processDocument(nextDocument.getPdfPath(), nextDocument.getDocumentId());
        }
    }
}

