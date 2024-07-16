package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.Table_extractions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableExtractionsRepository extends MongoRepository<Table_extractions, String> {
    Table_extractions findByDocumentId(String documentId);

    Table_extractions findTable_extractionsById(String id);
}
