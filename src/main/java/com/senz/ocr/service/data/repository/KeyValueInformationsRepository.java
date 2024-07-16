package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.Key_value_informations;
import com.senz.ocr.service.data.entity.Table_extractions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyValueInformationsRepository extends MongoRepository<Key_value_informations, String> {
    Key_value_informations findByDocumentId(String documentId);

    Key_value_informations findKey_value_informationsById(String id);
}
