package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public List<Notification> getNotifications(@RequestParam(required = false) LocalDateTime start,
                                               @RequestParam(required = false) LocalDateTime end) {
        // if start is empty
        // get all notifications

        // if start and end is empty
        // get all notifications

        // if start is not empty and end is empty
        // get all notifications from the start

        // if start and end not empty
        // get all notifications within range
        return notificationService.getNotifications(start, end);
    }

    @GetMapping("/{notificationId}")
    public Notification getNotification(@PathVariable Long notificationId) {
        // return a single notification
        return null;
    }

    @PostMapping()
    public Notification addNotification(@RequestBody Notification notification) {
        // create notification
        return null;
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
