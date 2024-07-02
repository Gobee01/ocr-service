package com.senz.ocr.service.data.dto;

import java.util.List;
import java.util.Map;

public class ExtractionDTO {

    private Integer documentId;

    private String documentName;

    private Map<String, List<List<String>>> keyValues;

    private Map<String, Map<String, List<Map<String, Object>>>> tableExtraction;

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
        return "ExtractionDTO{" +
                ", documentName='" + documentName + '\'' +
                ", documentId='" + documentId +
                ", keyValues=" + keyValues +
                ", tableExtraction=" + tableExtraction +
                '}';
    }

}
