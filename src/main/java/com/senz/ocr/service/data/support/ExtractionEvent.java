package com.senz.ocr.service.data.support;

public class ExtractionEvent {
    private String pdfFilePath;
    private Integer documentId;

    public ExtractionEvent(String pdfFilePath, Integer documentId) {
        this.pdfFilePath = pdfFilePath;
        this.documentId = documentId;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public Integer getDocumentId() {
        return documentId;
    }

}
