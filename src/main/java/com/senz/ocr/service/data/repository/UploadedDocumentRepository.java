package com.senz.ocr.service.data.repository;

import com.senz.ocr.service.data.entity.UploadedDocument;
import com.senz.ocr.service.data.support.ExtractionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UploadedDocumentRepository extends MongoRepository<UploadedDocument, String> {
    List<UploadedDocument> findAllByStatus(ExtractionStatus status);

    UploadedDocument findUploadedDocumentById(String id);

    Page<UploadedDocument> findAllBy(Pageable pageable);

}
