package com.senz.ocr.service.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Key_value_informations {
    @Id
    private String id;

    private String documentId;

    private String documentName;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime updatedAt;

    private Map<String, List<List<String>>> updatedKeyValueExtraction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, List<List<String>>> getUpdatedKeyValueExtraction() {
        return updatedKeyValueExtraction;
    }

    public void setUpdatedKeyValueExtraction(Map<String, List<List<String>>> updatedKeyValueExtraction) {
        this.updatedKeyValueExtraction = updatedKeyValueExtraction;
    }

    @Override
    public String toString() {
        return "Extraction{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", keyValues=" + updatedKeyValueExtraction +
                '}';
    }
}
