package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.Classifications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassificationsRepository extends MongoRepository<Classifications, String> {
    Classifications findByDocumentId(Integer documentId);

    Classifications findClassificationsById(String id);
}
