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

    UploadedDocument findByDocumentId(Integer documentId);

    Page<UploadedDocument> findAllBy(Pageable pageable);

    Boolean existsByStatus(ExtractionStatus status);

    UploadedDocument findFirstByStatusOrderByIdAsc(ExtractionStatus status);
}
