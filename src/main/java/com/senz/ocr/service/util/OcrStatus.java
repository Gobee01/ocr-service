package com.senz.ocr.service.util;

public enum OcrStatus {
    SUCCESS("SSA1000", "Success"),
    BAD_REQUEST("400", "Bad request"),
    ERROR("ESA1000", "Unknown error occurred in operation"),
    MISSING_REQUIRED_PARAMS("ESA1001", "One or more required parameters are missing"),
    INVALID_INPUT("ESA1002", "Unknown error occurred in database operation"),
    DB_ERROR("ESA1003", "Unknown error occurred in database operation"),
    DONT_HAVE_PERMISSION("ESA1004", "You don't have Permission to do this"),
    INVALID_DATA("ESA1005", "Invalid Data"),
    DOCUMENT_NOT_FOUND("ESA1010", "Document not found"),
    EXTRACTION_DATA_NOT_FOUND("ESA1011", "Extraction Data not found");

    private final String statusCode;
    private final String statusDescription;

    OcrStatus(String statusCode, String successDescription) {
        this.statusCode = statusCode;
        this.statusDescription = successDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public boolean isSuccess() {
        return this.statusCode.equals(OcrStatus.SUCCESS.statusCode);
    }
}
