package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotifications(LocalDateTime start, LocalDateTime end) {
        if (start == null && end == null) return getNotification();
        return null;
    }

    public List<Notification> getNotification() {
        return (List<Notification>) notificationRepository.findAll();
    }
}
