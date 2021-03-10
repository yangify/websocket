package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.notification.NotificationNotFoundException;
import com.gov.dsta.coldstraw.model.Group;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.repository.NotificationReceiverRepository;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import com.gov.dsta.coldstraw.service.notification.NotificationConstructorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationConstructorService notificationConstructorService;
    private final NotificationRepository notificationRepository;
    private final NotificationReceiverRepository notificationReceiverRepository;

    public NotificationService(NotificationConstructorService notificationConstructorService,
                               NotificationRepository notificationRepository,
                               NotificationReceiverRepository notificationReceiverRepository) {

        this.notificationConstructorService = notificationConstructorService;
        this.notificationRepository = notificationRepository;
        this.notificationReceiverRepository = notificationReceiverRepository;
    }

    public Notification getNotification(UUID notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public Notification createNotification(Notification notification) {
        notificationConstructorService.create(notification);
        Notification savedNotification = notificationRepository.save(notification);
        Set<NotificationReceiver> receivers = mergeGroupsAndReceivers(savedNotification);
        receivers.forEach(notificationReceiverRepository::save);
        return savedNotification;
    }

    private Set<NotificationReceiver> mergeGroupsAndReceivers(Notification notification) {
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
