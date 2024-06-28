package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.DocumentCounter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentCounterRepository extends MongoRepository<DocumentCounter, String> {

    DocumentCounter findDocumentCounterById(String id);

    DocumentCounter findByUniqueName(String name);
}
