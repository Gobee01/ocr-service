package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.Extraction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExtractionRepository extends MongoRepository<Extraction, String> {
    Extraction findByDocumentId(Integer documentId);

    Extraction findExtractionById(String id);
}
