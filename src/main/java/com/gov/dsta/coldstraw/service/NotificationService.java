package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotifications(Date start, Date end) {
        if (start == null && end == null)   return getNotifications();
        if (start == null)                  return getNotificationsBefore(end);
        if (end == null)                    return getNotificationsAfter(start);
        return getNotificationsBetween(start, end);
    }

    public List<Notification> getNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    public List<Notification> getNotificationsBefore(Date end) {
        return null;
    }

    public List<Notification> getNotificationsAfter(Date start) {
        return null;
    }

    public List<Notification> getNotificationsBetween(Date start, Date end) {
        return null;
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
