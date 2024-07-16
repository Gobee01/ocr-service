package com.senz.ocr.service.service;

import com.senz.ocr.service.data.dto.UploadedDocumentDTO;
import com.senz.ocr.service.data.entity.DocumentCounter;
import com.senz.ocr.service.data.entity.UploadedDocument;
import com.senz.ocr.service.data.repository.DocumentCounterRepository;
import com.senz.ocr.service.data.repository.UploadedDocumentRepository;
import com.senz.ocr.service.data.support.ExtractionEvent;
import com.senz.ocr.service.data.support.ExtractionStatus;
import com.senz.ocr.service.util.OcrException;
import com.senz.ocr.service.util.OcrStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadedDocumentService {

    @Autowired
    UploadedDocumentRepository uploadedDocumentRepository;

    @Autowired
    DocumentCounterRepository documentCounterRepository;

    @Autowired
    ExtractionService extractionService;

    @Autowired
    private ApplicationEventPublisher publisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadedDocumentService.class);

    public Integer getLastDocumentId(String counterName) {
        DocumentCounter counter = documentCounterRepository.findByUniqueName(counterName);
        if (counter == null) {
            counter = new DocumentCounter();
            counter.setUniqueName(counterName);
            counter.setLastDocumentId(0); // Initialize if not found
            documentCounterRepository.save(counter);
        } else {
            counter.setLastDocumentId(counter.getLastDocumentId() + 1);
            documentCounterRepository.save(counter);
        }

        return counter.getLastDocumentId();
    }

    public List<UploadedDocument> getAllDocuments() {
        LOGGER.debug("Getting All Documents");

        return uploadedDocumentRepository.findAll();
    }

    public UploadedDocument getUploadedDocument(String documentId) {
        LOGGER.debug("Getting Document: {}", documentId);

        return uploadedDocumentRepository.findUploadedDocumentById(documentId);
    }

    public Page<UploadedDocument> getDocumentPage(int pageIndex, int pageSize) {
        LOGGER.debug("Get All Documents, in page no:{}", pageIndex);
        Page<UploadedDocument> returnPage = null;

        if (pageSize == 0) {
            pageSize = Integer.MAX_VALUE;
        }

        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "documentId"));
        returnPage = uploadedDocumentRepository.findAllBy(pageRequest);

        return returnPage;
    }

    public UploadedDocument addDocument(UploadedDocumentDTO documentDTO) {
        LOGGER.debug("Request to upload new Document : {}", documentDTO);

        UploadedDocument document = new UploadedDocument();
        BeanUtils.copyProperties(documentDTO, document);
        document.setDocumentId(getLastDocumentId("counter"));

        if (document.getStatus() == null) {
            document.setStatus(ExtractionStatus.IN_QUEUE);
        }

        uploadedDocumentRepository.save(document);

        boolean isExtractionInProgress = uploadedDocumentRepository.existsByStatus(ExtractionStatus.EXTRACTION_IN_PROGRESS);
        if (!isExtractionInProgress) {
            // Start the document processing
            document.setStatus(ExtractionStatus.EXTRACTION_IN_PROGRESS);
            uploadedDocumentRepository.save(document);
            this.publisher.publishEvent(new ExtractionEvent(document.getPdfPath(), document.getDocumentId()));
        }

        return document;
    }

    public UploadedDocument updateDocument(UploadedDocumentDTO documentDTO, String documentId) {
        LOGGER.debug("Request to Update Document: {}, Details: {}", documentId, documentDTO);

        UploadedDocument document = uploadedDocumentRepository.findUploadedDocumentById(documentId);
        BeanUtils.copyProperties(documentDTO, document);

//        if (document.getStatus() == null) {
//            document.setStatus(ExtractionStatus.IN_QUEUE);
//        }

        return uploadedDocumentRepository.save(document);
    }

    public String deleteDocument(String documentId) {
        LOGGER.debug("Request to Delete Document : {}", documentId);
        UploadedDocument document = uploadedDocumentRepository.findUploadedDocumentById(documentId);

        if (document == null) {
            throw new OcrException(OcrStatus.DOCUMENT_NOT_FOUND);
        }

        extractionService.deleteExtraction(document.getDocumentId());
        extractionService.deleteKeyValues(document.getDocumentId());
        extractionService.deleteClassification(document.getDocumentId());
        uploadedDocumentRepository.delete(document);
        return "SuccessFully Deleted";
    }

}
