package com.senz.ocr.service.api;

import com.senz.ocr.service.data.entity.UploadedDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketApi {

    @Autowired
    private SimpMessagingTemplate template;

    public void notifyExtractionComplete(UploadedDocument document) {
        this.template.convertAndSend("/topic/extractionStatus", document);
    }
}

