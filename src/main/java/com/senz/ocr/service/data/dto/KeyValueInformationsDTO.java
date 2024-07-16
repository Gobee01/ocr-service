package com.senz.ocr.service.data.dto;

import java.util.List;
import java.util.Map;

public class KeyValueInformationsDTO {

    private String documentId;

    private String documentName;

    private Map<String, List<List<String>>> updatedKeyValueExtraction;

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

    public Map<String, List<List<String>>> getUpdatedKeyValueExtraction() {
        return updatedKeyValueExtraction;
    }

    public void setUpdatedKeyValueExtraction(Map<String, List<List<String>>> updatedKeyValueExtraction) {
        this.updatedKeyValueExtraction = updatedKeyValueExtraction;
    }

    @Override
    public String toString() {
        return "ExtractionDTO{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", keyValues=" + updatedKeyValueExtraction +
                '}';
    }

}
