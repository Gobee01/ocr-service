package com.senz.ocr.service.service;

import com.senz.ocr.service.data.dto.ClassificationsDTO;
import com.senz.ocr.service.data.dto.KeyValueInformationsDTO;
import com.senz.ocr.service.data.dto.tableExtractionsDTO;
import com.senz.ocr.service.data.entity.Classifications;
import com.senz.ocr.service.data.entity.Key_value_informations;
import com.senz.ocr.service.data.entity.Table_extractions;
import com.senz.ocr.service.data.repository.ClassificationsRepository;
import com.senz.ocr.service.data.repository.KeyValueInformationsRepository;
import com.senz.ocr.service.data.repository.TableExtractionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractionService {

    @Autowired
    TableExtractionsRepository tableExtractionsRepository;

    @Autowired
    KeyValueInformationsRepository keyValueInformationsRepository;

    @Autowired
    ClassificationsRepository classificationsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractionService.class);

    public List<Table_extractions> getAllExtraction() {
        LOGGER.debug("Getting All Extraction Data");

        return tableExtractionsRepository.findAll();
    }

    public Table_extractions getExtraction(Integer documentId) {
        LOGGER.debug("Getting Extraction Data for DocumentID: {}", documentId);

        return tableExtractionsRepository.findByDocumentId(documentId.toString());
    }

    public Table_extractions addExtraction(tableExtractionsDTO tableExtractionsDTO) {
        LOGGER.debug("Request to add new Extraction : {}", tableExtractionsDTO);

        Table_extractions tableExtractions = new Table_extractions();
        BeanUtils.copyProperties(tableExtractionsDTO, tableExtractions);

        return tableExtractionsRepository.save(tableExtractions);
    }

    public Table_extractions updateExtraction(tableExtractionsDTO tableExtractionsDTO, String extractionId) {
        LOGGER.debug("Request to Update Extraction: {}, Details: {}", extractionId, tableExtractionsDTO);

        Table_extractions tableExtractions = tableExtractionsRepository.findTable_extractionsById(extractionId);
        BeanUtils.copyProperties(tableExtractionsDTO, tableExtractions);

        return tableExtractionsRepository.save(tableExtractions);
    }

    public void deleteExtraction(Integer documentId) {
        LOGGER.debug("Request to Delete Extraction For DocumentID : {}", documentId);
        Table_extractions tableExtractions = tableExtractionsRepository.findByDocumentId(documentId.toString());

        if (tableExtractions != null) {
            tableExtractionsRepository.delete(tableExtractions);
        }
    }

    public List<Key_value_informations> getAllKeyValues() {
        LOGGER.debug("Getting All Extraction Data");

        return keyValueInformationsRepository.findAll();
    }

    public Key_value_informations getKeyValues(Integer documentId) {
        LOGGER.debug("Getting KeyValues Data for DocumentID: {}", documentId);

        return keyValueInformationsRepository.findByDocumentId(documentId.toString());
    }

    public Key_value_informations addKeyValues(KeyValueInformationsDTO keyValueInformationsDTO) {
        LOGGER.debug("Request to add new KeyValues : {}", keyValueInformationsDTO);

        Key_value_informations keyValueInformations = new Key_value_informations();
        BeanUtils.copyProperties(keyValueInformationsDTO, keyValueInformations);

        return keyValueInformationsRepository.save(keyValueInformations);
    }

    public Key_value_informations updateKeyValues(KeyValueInformationsDTO keyValueInformationsDTO, String extractionId) {
        LOGGER.debug("Request to Update KeyValues: {}, Details: {}", extractionId, keyValueInformationsDTO);

        Key_value_informations keyValueInformations = keyValueInformationsRepository.findKey_value_informationsById(extractionId);
        BeanUtils.copyProperties(keyValueInformationsDTO, keyValueInformations);

        return keyValueInformationsRepository.save(keyValueInformations);
    }

    public void deleteKeyValues(Integer documentId) {
        LOGGER.debug("Request to Delete KeyValues For DocumentID : {}", documentId);
        Key_value_informations keyValueInformations = keyValueInformationsRepository.findByDocumentId(documentId.toString());

        if (keyValueInformations != null) {
            keyValueInformationsRepository.delete(keyValueInformations);
        }
    }

    public List<Classifications> getAllClassification() {
        LOGGER.debug("Getting All Extraction Data");

        return classificationsRepository.findAll();
    }

    public Classifications getClassification(Integer documentId) {
        LOGGER.debug("Getting Classification Data for DocumentID: {}", documentId);

        return classificationsRepository.findByDocumentId(documentId);
    }

    public Classifications addClassification(ClassificationsDTO classificationsDTO) {
        LOGGER.debug("Request to add new Classification : {}", classificationsDTO);

        Classifications classifications = new Classifications();
        BeanUtils.copyProperties(classificationsDTO, classifications);

        return classificationsRepository.save(classifications);
    }

    public void deleteClassification(Integer documentId) {
        LOGGER.debug("Request to Delete Classification For DocumentID : {}", documentId);
        Classifications classifications = classificationsRepository.findByDocumentId(documentId);

        if (classifications != null) {
            classificationsRepository.delete(classifications);
        }
    }
}
