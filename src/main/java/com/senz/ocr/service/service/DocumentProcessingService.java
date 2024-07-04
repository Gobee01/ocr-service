package com.senz.ocr.service.service;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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

    @Transactional
    public void processDocument(String pdfFilePath, Integer documentId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String extractedPath = extractFilePath(pdfFilePath);
        String fullPath = "/app/gen_OCR/" + extractedPath;

        String requestBody = String.format("{\"pdf_file_path\": \"%s\", \"_id\": \"%s\"}", fullPath, documentId.toString());
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        LOGGER.debug("Starting extraction for Document: " + documentId);
        try {
            ResponseEntity<String> response = restTemplate.exchange(processPdfUrl, HttpMethod.POST, entity, String.class);
            UploadedDocument document = uploadedDocumentRepository.findByDocumentId(documentId);

            // Handle response and update status accordingly
            if (response.getStatusCode().is2xxSuccessful()) {
                LOGGER.debug("Successful Response:" + response.getBody());
                document.setStatus(ExtractionStatus.VALIDATION_PENDING);
            } else {
                LOGGER.debug("Failed Response:" + response);
                document.setStatus(ExtractionStatus.EXTRACTION_FAILED);
            }
            uploadedDocumentRepository.save(document);
        } catch (Exception e) {
            LOGGER.error("Error processing document: " + documentId, e);
            UploadedDocument document = uploadedDocumentRepository.findByDocumentId(documentId);
            document.setStatus(ExtractionStatus.EXTRACTION_FAILED);
            uploadedDocumentRepository.save(document);
        }

        // Process the next document in the queue
        processNextInQueue();
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

