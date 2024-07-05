package com.senz.ocr.service.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessPdfResponseDTO {
    private Map<String, Map<String, String>> classification;

    private Map<String, List<List<String>>> key_vlaue_information;
    private String mongo_id;
    private String status;
    private Map<String, Map<String, List<Map<String, Object>>>> table_extraction;

    // Getters and Setters

    public Map<String, Map<String, String>> getClassification() {
        return classification;
    }

    public void setClassification(Map<String, Map<String, String>> classification) {
        this.classification = classification;
    }

    public String getMongo_id() {
        return mongo_id;
    }

    public void setMongo_id(String mongo_id) {
        this.mongo_id = mongo_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Map<String, List<Map<String, Object>>>> getTable_extraction() {
        return table_extraction;
    }

    public void setTable_extraction(Map<String, Map<String, List<Map<String, Object>>>> table_extraction) {
        this.table_extraction = table_extraction;
    }

    public Map<String, List<List<String>>> getKey_vlaue_information() {
        return key_vlaue_information;
    }

    public void setKey_vlaue_information(Map<String, List<List<String>>> key_vlaue_information) {
        this.key_vlaue_information = key_vlaue_information;
    }
}

