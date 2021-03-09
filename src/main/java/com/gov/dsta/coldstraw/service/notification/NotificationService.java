package com.gov.dsta.coldstraw.service.notification;

import com.gov.dsta.coldstraw.exception.notification.NotificationNotFoundException;
import com.gov.dsta.coldstraw.model.*;
import com.gov.dsta.coldstraw.repository.NotificationReceiverRepository;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import com.gov.dsta.coldstraw.service.GroupService;
import com.gov.dsta.coldstraw.service.ModuleService;
import com.gov.dsta.coldstraw.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationService {

    private final NotificationCreator notificationCreator;
    private final NotificationUpdater notificationUpdater;
    private final NotificationRepository notificationRepository;
    private final NotificationReceiverRepository notificationReceiverRepository;

    public NotificationService(NotificationCreator notificationCreator,
                               NotificationUpdater notificationUpdater,
                               NotificationRepository notificationRepository,
                               NotificationReceiverRepository notificationReceiverRepository) {

        this.notificationCreator = notificationCreator;
        this.notificationUpdater = notificationUpdater;
        this.notificationRepository = notificationRepository;
        this.notificationReceiverRepository = notificationReceiverRepository;
    }

    public List<Notification> getNotificationsByDateAndPage(Date startDate, Date endDate,
                                                            Integer page,   Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return getNotificationsByDate(startDate, endDate, pageable);
    }

    public List<Notification> getNotificationsByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return getNotifications(pageable);
    }

    public List<Notification> getNotificationsByDate(Date start, Date end, Pageable pageable) {
        if (start == null && end == null)   return getNotifications(pageable);
        if (start == null)                  return getNotificationsBefore(end, pageable);
        if (end == null)                    return getNotificationsAfter(start, pageable);
        return getNotificationsBetween(start, end, pageable);
    }

    public List<Notification> getNotifications(Pageable pageable) {
        if (pageable == null) return getNotifications();
        Page<Notification> pageNotifications = notificationRepository.findAll(pageable);
        return pageNotifications.getContent();
    }

    public List<Notification> getNotifications() {
        return (List<Notification>) notificationRepository.findAll();
    }

    public List<Notification> getNotificationsBefore(Date end, Pageable pageable) {
        if (pageable == null) return getNotificationsBefore(end);
        return notificationRepository.findAllByDateBefore(end, pageable);
    }

    public List<Notification> getNotificationsBefore(Date end) {
        return notificationRepository.findAllByDateBefore(end);
    }

    public List<Notification> getNotificationsAfter(Date start, Pageable pageable) {
        if (pageable == null) return getNotificationsAfter(start);
        return notificationRepository.findAllByDateAfter(start, pageable);
    }

    public List<Notification> getNotificationsAfter(Date start) {
        return notificationRepository.findAllByDateAfter(start);
    }

    public List<Notification> getNotificationsBetween(Date start, Date end, Pageable pageable) {
        if (pageable == null) return getNotificationsBetween(start, end);
        return notificationRepository.findAllByDateBetween(start, end, pageable);
    }

    public List<Notification> getNotificationsBetween(Date start, Date end) {
        return notificationRepository.findAllByDateBetween(start, end);
    }

    public Notification getNotification(UUID notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public Notification createNotification(Notification notification) {
        notificationCreator.create(notification);
        Notification savedNotification = notificationRepository.save(notification);
        mergeReceivers(notification).forEach(notificationReceiverRepository::save);
        return savedNotification;
    }

    public Notification updateNotification(UUID notificationId, Notification notification) {
        Notification originalNotification = getNotification(notificationId);
        notificationUpdater.updateReadStatus(originalNotification, notification);
        return notificationRepository.save(originalNotification);
    }

    private Set<NotificationReceiver> mergeReceivers(Notification notification) {
        Set<NotificationReceiver> allReceivers = notification.getReceivers();
        Set<Group> groups = notification.getGroups();
        if (groups.isEmpty()) return allReceivers;
        groups.forEach(group -> group.getUsers().forEach(user -> {
                NotificationReceiver nr = new NotificationReceiver()
                        .setReceiver(user)
                        .setNotification(notification);
                allReceivers.add(nr);
            })
        );
        return allReceivers;
    }
}
