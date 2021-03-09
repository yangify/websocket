package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.service.CountService;
import com.gov.dsta.coldstraw.service.NotificationReceiverService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ReceiverController {

    private final CountService countService;

    public ReceiverController(CountService countService) {
        this.countService = countService;
    }

    // TODO update endpoint to proper name and change frontend link
    @GetMapping("/notifications/count")
    public Count getCount() {
        return countService.getCount("Tom");
    }
}
