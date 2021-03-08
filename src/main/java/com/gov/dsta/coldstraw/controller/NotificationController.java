package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.NotificationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping()
    public List<Notification> getNotifications(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        if (requireDate(startDate, endDate) && requirePagination(page, size))
            return notificationService.getNotificationsByDateAndPage(startDate, endDate, page, size);

        if (requireDate(startDate, endDate))
            return notificationService.getNotificationsByDate(startDate, endDate, null);

        if (requirePagination(page, size))
            return notificationService.getNotificationsByPage(page, size);

        return notificationService.getNotifications();
    }

    @GetMapping("/{notificationId}")
    public Notification getNotification(@PathVariable UUID notificationId) {
        return notificationService.getNotification(notificationId);
    }

    @PostMapping()
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{notificationId}")
    public Notification updateNotification(@PathVariable UUID notificationId,
                                           @RequestBody Notification notification) {
        return notificationService.updateNotification(notificationId, notification);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        // delete notification from receiver
    }

    private boolean requirePagination(Integer page, Integer size) {
        return page != null && size != null;
    }

    private boolean requireDate(Date startDate, Date endDate) {
        return startDate != null || endDate != null;
    }
}
