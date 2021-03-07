package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.NotificationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public List<Notification> getNotifications(@RequestParam(required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return notificationService.getNotifications(start, end);
    }

    @GetMapping("/{notificationId}")
    public Notification getNotification(@PathVariable Long notificationId) {
        // return a single notification
        return null;
    }

    @PostMapping()
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{notificationId}")
    public Notification updateNotification(@PathVariable Long notificationId, @RequestBody Notification notification) {
        // update read status
        return null;
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        // delete notification from receiver
    }
}
