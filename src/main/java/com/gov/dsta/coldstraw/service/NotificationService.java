package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.notification.NotificationNotFoundException;
import com.gov.dsta.coldstraw.model.*;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.repository.NotificationRepository;
import com.gov.dsta.coldstraw.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final ModuleService moduleService;
    private final UserService userService;
    private final GroupService groupService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(ModuleService moduleService,
                               UserService userService,
                               GroupService groupService,
                               NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.moduleService = moduleService;
        this.userService = userService;
        this.groupService = groupService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
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
        craftModule(notification);
        craftSender(notification);
        craftReceivers(notification);
        craftGroups(notification);
        return notificationRepository.save(notification);
    }

    private void craftModule(Notification notification) {
        String moduleName = notification.getModule().getName();
        Module module = moduleService.getModule(moduleName);
        notification.setModule(module);
    }

    private void craftSender(Notification notification) {
        String senderName = notification.getSender().getName();
        User sender = userService.getUser(senderName);
        notification.setSender(sender);
    }

    private void craftReceivers(Notification notification) {
        Set<User> receivers = notification
                .getReceivers()
                .stream()
                .map(rawReceiver -> {
                    String receiverName = rawReceiver.getName();
                    return userService.getUser(receiverName);
                })
                .collect(Collectors.toSet());
        notification.setReceivers(receivers);
    }

    private void craftGroups(Notification notification) {
        Set<Group> groups = notification
                .getGroups()
                .stream()
                .map(group -> {
                    String groupName = group.getName();
                    return groupService.getGroup(groupName);
                })
                .collect(Collectors.toSet());
        notification.setGroups(groups);
    }
}
