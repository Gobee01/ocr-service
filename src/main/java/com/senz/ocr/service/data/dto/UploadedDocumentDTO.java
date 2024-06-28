package com.senz.ocr.service.data.dto;

import com.senz.ocr.service.data.support.ExtractionStatus;

public class UploadedDocumentDTO {
    private String name;

    private String pdfPath;

    private ExtractionStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public ExtractionStatus getStatus() {
        return status;
    }

    public void setStatus(ExtractionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UploadedDocumentDTO{" +
                ", name='" + name + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", status=" + status +
                '}';
    }

}
