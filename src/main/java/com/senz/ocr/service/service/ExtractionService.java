package com.senz.ocr.service.service;

import com.senz.ocr.service.data.dto.ClassificationDTO;
import com.senz.ocr.service.data.dto.ExtractionDTO;
import com.senz.ocr.service.data.entity.Classification;
import com.senz.ocr.service.data.entity.Extraction;
import com.senz.ocr.service.data.repository.ClassificationRepository;
import com.senz.ocr.service.data.repository.ExtractionRepository;
import com.senz.ocr.service.util.OcrException;
import com.senz.ocr.service.util.OcrStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractionService {

    @Autowired
    ExtractionRepository extractionRepository;

    @Autowired
    ClassificationRepository classificationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractionService.class);

    public List<Extraction> getAllExtraction() {
        LOGGER.debug("Getting All Extraction Data");

        return extractionRepository.findAll();
    }

    public Extraction getExtraction(Integer documentId) {
        LOGGER.debug("Getting Extraction Data for DocumentID: {}", documentId);

        return extractionRepository.findByDocumentId(documentId);
    }

    public Extraction addExtraction(ExtractionDTO extractionDTO) {
        LOGGER.debug("Request to add new Extraction : {}", extractionDTO);

        Extraction extraction = new Extraction();
        BeanUtils.copyProperties(extractionDTO, extraction);

        return extractionRepository.save(extraction);
    }

    public Extraction updateExtraction(ExtractionDTO extractionDTO, String extractionId) {
        LOGGER.debug("Request to Update Extraction: {}, Details: {}", extractionId, extractionDTO);

        Extraction extraction = extractionRepository.findExtractionById(extractionId);
        BeanUtils.copyProperties(extractionDTO, extraction);

        return extractionRepository.save(extraction);
    }

    public void deleteExtraction(Integer documentId) {
        LOGGER.debug("Request to Delete Extraction For DocumentID : {}", documentId);
        Extraction extraction = extractionRepository.findByDocumentId(documentId);

        if (extraction != null) {
            extractionRepository.delete(extraction);
        }
    }

    public List<Classification> getAllClassification() {
        LOGGER.debug("Getting All Extraction Data");

        return classificationRepository.findAll();
    }

    public Classification getClassification(Integer documentId) {
        LOGGER.debug("Getting Classification Data for DocumentID: {}", documentId);

        return classificationRepository.findByDocumentId(documentId);
    }

    public Classification addClassification(ClassificationDTO classificationDTO) {
        LOGGER.debug("Request to add new Classification : {}", classificationDTO);

        Classification classification = new Classification();
        BeanUtils.copyProperties(classificationDTO, classification);

        return classificationRepository.save(classification);
    }

    public void deleteClassification(Integer documentId) {
        LOGGER.debug("Request to Delete Classification For DocumentID : {}", documentId);
        Classification classification = classificationRepository.findByDocumentId(documentId);

        if (classification != null) {
            classificationRepository.delete(classification);
        }
    }
}
