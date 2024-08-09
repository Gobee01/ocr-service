package com.senz.ocr.service.data.dto;

import java.util.Map;

public class ClassificationsDTO {

    private String documentId;

    private String documentName;

    private Map<String, Map<String, String>> updatedClassification;

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

    public Map<String, Map<String, String>> getUpdatedClassification() {
        return updatedClassification;
    }

    public void setUpdatedClassification(Map<String, Map<String, String>> updatedClassification) {
        this.updatedClassification = updatedClassification;
    }

    @Override
    public String toString() {
        return "Classification{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", keyValues=" + updatedClassification +
                '}';
    }

}
