package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.NotificationService;
import com.gov.dsta.coldstraw.service.WebsocketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;
    private final WebsocketService websocketService;

    public NotificationController(NotificationService notificationService,
                                  WebsocketService websocketService) {

        this.notificationService = notificationService;
        this.websocketService = websocketService;
    }

    @PostMapping()
    public Notification addNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.createNotification(notification);
        websocketService.publishNotification(savedNotification);
        websocketService.publishCount();
        return savedNotification;
    }
}
