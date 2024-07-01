package com.senz.ocr.service.api;

import com.magma.util.MagmaResponse;
import com.senz.ocr.service.data.dto.UploadedDocumentDTO;
import com.senz.ocr.service.data.entity.UploadedDocument;
import com.senz.ocr.service.service.UploadedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)  //TODO: Need To Specify Domain
public class UploadedDocumentApi {

    @Autowired
    UploadedDocumentService uploadedDocumentService;

    @RequestMapping(value = "api/allDocuments" , method = RequestMethod.GET)
    public MagmaResponse<List<UploadedDocument>> getAllDocuments(){
        return new MagmaResponse<>(uploadedDocumentService.getAllDocuments());
    }

    @RequestMapping(value = "api/document/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<UploadedDocument> getUploadedDocument(@PathVariable("documentId") String documentId){
        return new MagmaResponse<>(uploadedDocumentService.getUploadedDocument(documentId));
    }

    @RequestMapping(value = {"api/documentPage"}, method = {RequestMethod.GET})
    public MagmaResponse<Page<UploadedDocument>> getDocumentPage(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new MagmaResponse<>(uploadedDocumentService.getDocumentPage(pageIndex, pageSize));
    }

    @RequestMapping(value = "api/document", method = RequestMethod.POST)
    public MagmaResponse<UploadedDocument> addDocument(@RequestBody UploadedDocumentDTO documentDTO) {
        return new MagmaResponse<>(uploadedDocumentService.addDocument(documentDTO));
    }

    @RequestMapping(value = "api/document/{documentId}", method = RequestMethod.PUT)
    public MagmaResponse<UploadedDocument> updateDocument(@PathVariable("documentId") String documentId,
                                            @RequestBody UploadedDocumentDTO documentDTO) {
        return new MagmaResponse<>(uploadedDocumentService.updateDocument(documentDTO, documentId));
    }

    @RequestMapping(value = "api/document/{documentId}", method = RequestMethod.DELETE)
    public MagmaResponse<String> deleteDocument(@PathVariable("documentId") String documentId) {
        return new MagmaResponse<>(uploadedDocumentService.deleteDocument(documentId));
    }

}
