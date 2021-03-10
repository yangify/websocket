package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.service.CountService;
import com.gov.dsta.coldstraw.service.NotificationReceiverService;
import com.gov.dsta.coldstraw.service.NotificationService;
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

    private final CountService countService;
    private final NotificationService notificationService;
    private final NotificationReceiverService notificationReceiverService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(CountService countService, NotificationService notificationService, NotificationReceiverService notificationReceiverService, SimpMessagingTemplate simpMessagingTemplate) {
        this.countService = countService;
        this.notificationService = notificationService;
        this.notificationReceiverService = notificationReceiverService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping()
    public List<NotificationReceiver> getNotifications(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer size,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) throws InterruptedException {
        Thread.sleep(1000);
        if (requireDate(start, end) && requirePagination(page, size))
            return notificationReceiverService.getNotificationsByDateAndPage(start, end, page, size);

        if (requireDate(start, end))
            return notificationReceiverService.getNotificationsByDate(start, end, null);

        if (requirePagination(page, size))
            return notificationReceiverService.getNotificationsByPage(page, size);

        return notificationReceiverService.getNotifications();
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
    public NotificationReceiver updateNotification(@PathVariable UUID notificationId,
                                                   @RequestBody NotificationReceiver notificationReceiver) {
        NotificationReceiver savedNotificationReceiver = notificationReceiverService.updateNotification(notificationId, notificationReceiver);
        publishCount();
        return savedNotificationReceiver;
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable UUID notificationId) {
        notificationReceiverService.deleteNotification(notificationId);
        publishCount();
    }

    private boolean requirePagination(Integer page, Integer size) {
        return page != null && size != null;
    }

    private boolean requireDate(Date startDate, Date endDate) {
        return startDate != null || endDate != null;
    }

    public void publishNotification(Notification notification) {
        // TODO update to use user context
        NotificationReceiver notificationReceiver = notificationReceiverService.getNotification(notification);
        simpMessagingTemplate.convertAndSend("/topic/notifications", notificationReceiver);
    }

    public void publishCount() {
        Count count = countService.getCount("Tom");
        simpMessagingTemplate.convertAndSend("/topic/count", count);
    }
}
