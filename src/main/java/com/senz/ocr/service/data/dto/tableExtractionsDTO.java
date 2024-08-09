package com.senz.ocr.service.data.dto;

import java.util.List;
import java.util.Map;

public class tableExtractionsDTO {

    private String documentId;

    private String documentName;

    private Map<String, Map<String, List<Map<String, Object>>>> updatedExtraction;

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

    public Map<String, Map<String, List<Map<String, Object>>>> getUpdatedExtraction() {
        return updatedExtraction;
    }

    public void setUpdatedExtraction(Map<String, Map<String, List<Map<String, Object>>>> updatedExtraction) {
        this.updatedExtraction = updatedExtraction;
    }

    @Override
    public String toString() {
        return "ExtractionDTO{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", tableExtraction=" + updatedExtraction +
                '}';
    }

}
