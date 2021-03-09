package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.service.ReceiverService;
import com.gov.dsta.coldstraw.service.notification.NotificationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final ReceiverService receiverService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(ReceiverService receiverService, NotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.receiverService = receiverService;
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
        Notification savedNotification = notificationService.createNotification(notification);
        publishNotification(savedNotification);
        publishCount();
        return savedNotification;
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

    public void publishNotification(Notification notification) {
        simpMessagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    public void publishCount() {
        Count count = receiverService.getCount("Tom");
        simpMessagingTemplate.convertAndSend("/topic/count", count);
    }
}
