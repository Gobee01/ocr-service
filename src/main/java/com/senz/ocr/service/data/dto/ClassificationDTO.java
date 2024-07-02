package com.senz.ocr.service.data.dto;

import java.util.Map;

public class ClassificationDTO {

    private Integer documentId;

    private String documentName;

    private Map<String, Map<String, String>> classification;

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

    public Map<String, Map<String, String>> getClassification() {
        return classification;
    }

    public void setClassification(Map<String, Map<String, String>> classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "Classification{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", keyValues=" + classification +
                '}';
    }

}
