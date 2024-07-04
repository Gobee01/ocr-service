package com.senz.ocr.service.data.Listener;

import com.senz.ocr.service.data.support.ExtractionEvent;
import com.senz.ocr.service.service.DocumentProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExtractionListener {

    @Autowired
    DocumentProcessingService documentProcessingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractionListener.class);

    @Async
    @EventListener
    public void handler(ExtractionEvent extractionEvent) {
        LOGGER.debug("Listening Extraction Event to documentId : {}", extractionEvent.getDocumentId());
        documentProcessingService.processDocument(extractionEvent.getPdfFilePath(), extractionEvent.getDocumentId());
    }


}
