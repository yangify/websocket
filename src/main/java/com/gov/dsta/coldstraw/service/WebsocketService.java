package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private final NotificationReceiverService notificationReceiverService;
    private final CountService countService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketService(NotificationReceiverService notificationReceiverService,
                            CountService countService,
                            SimpMessagingTemplate simpMessagingTemplate) {

        this.notificationReceiverService = notificationReceiverService;
        this.countService = countService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void publishNotification(Notification notification) {
        NotificationReceiver notificationReceiver = notificationReceiverService.getNotificationReceiver(notification);
        simpMessagingTemplate.convertAndSend("/topic/notifications", notificationReceiver);
    }

    public void publishCount() {
        Count count = countService.getCount();
        simpMessagingTemplate.convertAndSend("/topic/count", count);
    }
}
