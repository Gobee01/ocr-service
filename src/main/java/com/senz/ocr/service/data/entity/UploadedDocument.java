package com.senz.ocr.service.data.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senz.ocr.service.data.support.ExtractionStatus;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class UploadedDocument {
    @Id
    private String id;

    private Integer documentId;

    private String name;

    @JsonIgnore
    @CreatedDate
    private DateTime uploadedDate;

    private String pdfPath;

    private ExtractionStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(DateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
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
        return "UploadedDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", documentId='" + documentId +
                ", pdfPath='" + pdfPath + '\'' +
                ", uploadedDate='" + uploadedDate +
                ", status=" + status +
                '}';
    }
}
