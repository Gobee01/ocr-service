package com.senz.ocr.service.util;

public class OcrException extends RuntimeException{

    private OcrStatus status;

    public OcrException(OcrStatus status) {
        super(status.getStatusDescription());
        this.status = status;
    }

    public OcrStatus getStatus() {
        return status;
    }

    public void setStatus(OcrStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OcrException{" +
                "status=" + status +
                '}';
    }
}
