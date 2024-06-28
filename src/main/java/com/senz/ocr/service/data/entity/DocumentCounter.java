package com.senz.ocr.service.data.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class DocumentCounter {
    @Id
    private String id;

    private String uniqueName;

    private int lastDocumentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public int getLastDocumentId() {
        return lastDocumentId;
    }

    public void setLastDocumentId(int lastDocumentId) {
        this.lastDocumentId = lastDocumentId;
    }

}
