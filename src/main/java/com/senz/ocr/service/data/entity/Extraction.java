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
public class Extraction {
    @Id
    private String id;

    @Indexed(unique = true)
    private Integer documentId;

    private String documentName;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Map<String, List<List<String>>> keyValues;

    private Map<String, Map<String, List<Map<String, Object>>>> tableExtraction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Map<String, List<List<String>>> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, List<List<String>>> keyValues) {
        this.keyValues = keyValues;
    }

    public Map<String, Map<String, List<Map<String, Object>>>> getTableExtraction() {
        return tableExtraction;
    }

    public void setTableExtraction(Map<String, Map<String, List<Map<String, Object>>>> tableExtraction) {
        this.tableExtraction = tableExtraction;
    }

    @Override
    public String toString() {
        return "Extraction{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", keyValues=" + keyValues +
                ", tableExtraction=" + tableExtraction +
                '}';
    }
}
