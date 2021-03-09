package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.service.ReceiverService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReceiverController {

    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    // TODO update endpoint to proper name and change frontend link
    @GetMapping("/v1/notifications/count")
    public Count getCount() {
        return receiverService.getCount("Tom");
    }
}
