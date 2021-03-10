package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.service.NotificationReceiverService;
import com.gov.dsta.coldstraw.service.WebsocketService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification/receiver")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationReceiverController {

    private final NotificationReceiverService notificationReceiverService;
    private final WebsocketService websocketService;

    public NotificationReceiverController(NotificationReceiverService notificationReceiverService,
                                          WebsocketService websocketService) {

        this.notificationReceiverService = notificationReceiverService;
        this.websocketService = websocketService;
    }

    @GetMapping()
    public List<NotificationReceiver> getNotificationReceivers(@RequestParam(required = false) Integer page,
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
    public NotificationReceiver getNotificationReceiver(@PathVariable UUID notificationId) {
        return notificationReceiverService.getNotification(notificationId);
    }

    @PutMapping("/{notificationId}")
    public NotificationReceiver updateNotificationReceiver(@PathVariable UUID notificationId,
                                                           @RequestBody NotificationReceiver notificationReceiver) {
        NotificationReceiver savedNotificationReceiver = notificationReceiverService.updateNotification(notificationId, notificationReceiver);
        websocketService.publishCount();
        return savedNotificationReceiver;
    }

    @PutMapping()
    public void readAllNotificationReceiver() {
        notificationReceiverService.readAllNotification();
        websocketService.publishCount();
    }

    @DeleteMapping()
    public void deleteAllNotificationReceiver() {
        notificationReceiverService.deleteAllNotification();
        websocketService.publishCount();
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotificationReceiver(@PathVariable UUID notificationId) {
        notificationReceiverService.deleteNotification(notificationId);
        websocketService.publishCount();
    }

    private boolean requirePagination(Integer page, Integer size) {
        return page != null && size != null;
    }

    private boolean requireDate(Date startDate, Date endDate) {
        return startDate != null || endDate != null;
    }
}
