package com.senz.ocr.service.api;

import com.magma.util.MagmaResponse;
import com.senz.ocr.service.data.dto.ClassificationsDTO;
import com.senz.ocr.service.data.dto.KeyValueInformationsDTO;
import com.senz.ocr.service.data.dto.tableExtractionsDTO;
import com.senz.ocr.service.data.entity.Classifications;
import com.senz.ocr.service.data.entity.Key_value_informations;
import com.senz.ocr.service.data.entity.Table_extractions;
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
    public MagmaResponse<List<Table_extractions>> getAllExtraction(){
        return new MagmaResponse<>(extractionService.getAllExtraction());
    }

    @RequestMapping(value = "api/extraction/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<Table_extractions> getExtraction(@PathVariable("documentId") Integer documentId){
        return new MagmaResponse<>(extractionService.getExtraction(documentId));
    }

    @RequestMapping(value = "api/extraction", method = RequestMethod.POST)
    public MagmaResponse<Table_extractions> addExtraction(@RequestBody tableExtractionsDTO tableExtractionsDTO) {
        return new MagmaResponse<>(extractionService.addExtraction(tableExtractionsDTO));
    }

    @RequestMapping(value = "api/extraction/{extractionId}", method = RequestMethod.PUT)
    public MagmaResponse<Table_extractions> updateExtraction(@PathVariable("extractionId") String extractionId,
                                                             @RequestBody tableExtractionsDTO tableExtractionsDTO) {
        return new MagmaResponse<>(extractionService.updateExtraction(tableExtractionsDTO, extractionId));
    }

    @RequestMapping(value = "api/allKeyValues" , method = RequestMethod.GET)
    public MagmaResponse<List<Key_value_informations>> getAllClassification(){
        return new MagmaResponse<>(extractionService.getAllKeyValues());
    }

    @RequestMapping(value = "api/keyValue/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<Key_value_informations> getKeyValues(@PathVariable("documentId") Integer documentId){
        return new MagmaResponse<>(extractionService.getKeyValues(documentId));
    }

    @RequestMapping(value = "api/keyValue", method = RequestMethod.POST)
    public MagmaResponse<Key_value_informations> addKeyValues(@RequestBody KeyValueInformationsDTO keyValueInformationsDTO) {
        return new MagmaResponse<>(extractionService.addKeyValues(keyValueInformationsDTO));
    }

    @RequestMapping(value = "api/keyValue/{extractionId}", method = RequestMethod.PUT)
    public MagmaResponse<Key_value_informations> updateExtraction(@PathVariable("extractionId") String extractionId,
                                                             @RequestBody KeyValueInformationsDTO keyValueInformationsDTO) {
        return new MagmaResponse<>(extractionService.updateKeyValues(keyValueInformationsDTO, extractionId));
    }

//    @RequestMapping(value = "api/extraction/{documentId}", method = RequestMethod.DELETE)
//    public MagmaResponse<String> deleteExtraction(@PathVariable("documentId") Integer documentId) {
//        return new MagmaResponse<>(extractionService.deleteExtraction(documentId));
//    }

    @RequestMapping(value = "api/classification/{documentId}" , method = RequestMethod.GET)
    public MagmaResponse<Classifications> getClassification(@PathVariable("documentId") Integer documentId){
        return new MagmaResponse<>(extractionService.getClassification(documentId));
    }

    @RequestMapping(value = "api/classification", method = RequestMethod.POST)
    public MagmaResponse<Classifications> addClassification(@RequestBody ClassificationsDTO classificationsDTO) {
        return new MagmaResponse<>(extractionService.addClassification(classificationsDTO));
    }
}
