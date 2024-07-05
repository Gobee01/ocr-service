package com.senz.ocr.service.api;

import com.magma.util.MagmaResponse;
import com.senz.ocr.service.data.dto.ClassificationDTO;
import com.senz.ocr.service.data.dto.ExtractionDTO;
import com.senz.ocr.service.data.entity.Classification;
import com.senz.ocr.service.data.entity.Extraction;
import com.senz.ocr.service.service.ExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)  //TODO: Need To Specify Domain
public class ExtractionApi {

    @Autowired
    ExtractionService extractionService;

    @RequestMapping(value = "api/allExtractions" , method = RequestMethod.GET)
    public MagmaResponse<List<Extraction>> getAllExtraction(){
        return new MagmaResponse<>(extractionService.getAllExtraction());
    }

    @RequestMapping(value = "api/extraction/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<Extraction> getExtraction(@PathVariable("documentId") Integer documentId){
        return new MagmaResponse<>(extractionService.getExtraction(documentId));
    }

    @RequestMapping(value = "api/extraction", method = RequestMethod.POST)
    public MagmaResponse<Extraction> addExtraction(@RequestBody ExtractionDTO extractionDTO) {
        return new MagmaResponse<>(extractionService.addExtraction(extractionDTO));
    }

    @RequestMapping(value = "api/extraction/{extractionId}", method = RequestMethod.PUT)
    public MagmaResponse<Extraction> updateExtraction(@PathVariable("extractionId") String extractionId,
                                                          @RequestBody ExtractionDTO extractionDTO) {
        return new MagmaResponse<>(extractionService.updateExtraction(extractionDTO, extractionId));
    }

//    @RequestMapping(value = "api/extraction/{documentId}", method = RequestMethod.DELETE)
//    public MagmaResponse<String> deleteExtraction(@PathVariable("documentId") Integer documentId) {
//        return new MagmaResponse<>(extractionService.deleteExtraction(documentId));
//    }

    @RequestMapping(value = "api/classification/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<Classification> getClassification(@PathVariable("documentId") Integer documentId){
        return new MagmaResponse<>(extractionService.getClassification(documentId));
    }

    @RequestMapping(value = "api/classification", method = RequestMethod.POST)
    public MagmaResponse<Classification> addClassification(@RequestBody ClassificationDTO classificationDTO) {
        return new MagmaResponse<>(extractionService.addClassification(classificationDTO));
    }
}
