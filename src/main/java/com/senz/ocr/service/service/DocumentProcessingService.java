package com.senz.ocr.service.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.senz.ocr.service.api.WebSocketApi;
import com.senz.ocr.service.data.dto.ProcessPdfResponseDTO;
import com.senz.ocr.service.data.entity.Classifications;
import com.senz.ocr.service.data.entity.Key_value_informations;
import com.senz.ocr.service.data.entity.Table_extractions;
import com.senz.ocr.service.data.entity.UploadedDocument;
import com.senz.ocr.service.data.repository.ClassificationsRepository;
import com.senz.ocr.service.data.repository.KeyValueInformationsRepository;
import com.senz.ocr.service.data.repository.TableExtractionsRepository;
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
    TableExtractionsRepository tableExtractionsRepository;

    @Autowired
    KeyValueInformationsRepository keyValueInformationsRepository;

    @Autowired
    ClassificationsRepository classificationsRepository;

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
//
//                saveClassificationData(responseDTO.getClassification(), documentId);
//                saveExtractionData(responseDTO.getTable_extraction(), documentId);
                document.setStatus(ExtractionStatus.EXTRACTION_COMPLETED);
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
        Classifications classifications = new Classifications();
        classifications.setDocumentId(documentId.toString());
        classifications.setUpdatedClassification(classificationData);
        classificationsRepository.save(classifications);
    }

    private void saveExtractionData(Map<String, Map<String, List<Map<String, Object>>>> tableExtraction, Integer documentId) {
        Table_extractions tableExtractions = new Table_extractions();
        tableExtractions.setDocumentId(documentId.toString());
        tableExtractions.setUpdatedExtraction(tableExtraction);
        tableExtractionsRepository.save(tableExtractions);
    }

    private void saveKeyValueInfo(Map<String, List<List<String>>> keyValues,Integer documentId) {
        Key_value_informations keyValueInformations = new Key_value_informations();
        keyValueInformations.setDocumentId(documentId.toString());
        keyValueInformations.setUpdatedKeyValueExtraction(keyValues);
        keyValueInformationsRepository.save(keyValueInformations);
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

