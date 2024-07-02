package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.Classification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassificationRepository extends MongoRepository<Classification, String> {
    Classification findByDocumentId(Integer documentId);

    Classification findClassificationById(String id);
}
