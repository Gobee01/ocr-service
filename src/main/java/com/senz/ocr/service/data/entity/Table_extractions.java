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
public class Table_extractions {
    @Id
    private String id;

    private String documentId;

    private String documentName;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime updatedAt;

    private Map<String, Map<String, List<Map<String, Object>>>> updatedExtraction;

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

    public Map<String, Map<String, List<Map<String, Object>>>> getUpdatedExtraction() {
        return updatedExtraction;
    }

    public void setUpdatedExtraction(Map<String, Map<String, List<Map<String, Object>>>> updatedExtraction) {
        this.updatedExtraction = updatedExtraction;
    }

    @Override
    public String toString() {
        return "Extraction{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", tableExtraction=" + updatedExtraction +
                '}';
    }
}
