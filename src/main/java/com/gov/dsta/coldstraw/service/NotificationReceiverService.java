package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.exception.notification.NotificationNotFoundException;
import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.model.embeddable.NotificationReceiverId;
import com.gov.dsta.coldstraw.repository.NotificationReceiverRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationReceiverService {

    private final UserService userService;
    private final NotificationReceiverRepository notificationReceiverRepository;

    // TODO update to user context
    private final String username = "Tom";

    public NotificationReceiverService(UserService userService, NotificationReceiverRepository notificationReceiverRepository) {
        this.userService = userService;
        this.notificationReceiverRepository = notificationReceiverRepository;
    }

    public List<NotificationReceiver> getNotificationsByDateAndPage(Date start, Date end,
                                                                    Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "notification.date"));
        return getNotificationsByDate(start, end, pageable);
    }

    public List<NotificationReceiver> getNotificationsByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "notification.date"));
        return getNotifications(pageable);
    }

    public List<NotificationReceiver> getNotificationsByDate(Date start, Date end, Pageable pageable) {
        if (start == null && end == null)   return getNotifications(pageable);
        if (start == null)                  return getNotificationsBefore(end, pageable);
        if (end == null)                    return getNotificationsAfter(start, pageable);
        return getNotificationsBetween(start, end, pageable);
    }

    public List<NotificationReceiver> getNotifications(Pageable pageable) {
        if (pageable == null) return getNotifications();
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByReceiver(receiver, pageable);
    }

    public List<NotificationReceiver> getNotifications() {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByReceiver(receiver);
    }

    public List<NotificationReceiver> getNotificationsBefore(Date end, Pageable pageable) {
        if (pageable == null) return getNotificationsBefore(end);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBefore(receiver, end, pageable);
    }

    public List<NotificationReceiver> getNotificationsBefore(Date end) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBefore(receiver, end);
    }

    public List<NotificationReceiver> getNotificationsAfter(Date start, Pageable pageable) {
        if (pageable == null) return getNotificationsAfter(start);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateAfter(receiver, start, pageable);
    }

    public List<NotificationReceiver> getNotificationsAfter(Date start) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateAfter(receiver, start);
    }

    public List<NotificationReceiver> getNotificationsBetween(Date start, Date end, Pageable pageable) {
        if (pageable == null) return getNotificationsBetween(start, end);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBetween(receiver, start, end, pageable);
    }

    public List<NotificationReceiver> getNotificationsBetween(Date start, Date end) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBetween(receiver, start, end);
    }

    public NotificationReceiver getNotification(Notification notification) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByNotificationAndReceiver(notification, receiver);
    }

    public NotificationReceiver getNotification(UUID notificationId) {
        User receiver = userService.getUser(username);
        NotificationReceiverId id = new NotificationReceiverId(receiver.getId(), notificationId);
        return notificationReceiverRepository
                .findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public NotificationReceiver updateNotification(UUID notificationReceiverId, NotificationReceiver notificationReceiver) {
        NotificationReceiver ogNotificationReceiver = getNotification(notificationReceiverId);
        ogNotificationReceiver.setRead(notificationReceiver.isRead());
        return notificationReceiverRepository.save(ogNotificationReceiver);
    }

    public void readAllNotification() {
        List<NotificationReceiver> notificationReceivers = getNotifications();
        notificationReceivers.forEach(notificationReceiver -> {
            notificationReceiver.setRead(true);
            notificationReceiverRepository.save(notificationReceiver);
        });
    }

    public void deleteAllNotification() {
        notificationReceiverRepository.deleteAll();
    }

    public void deleteNotification(UUID notificationId) {
        User receiver = userService.getUser(username);
        NotificationReceiverId id = new NotificationReceiverId(receiver.getId(), notificationId);
        notificationReceiverRepository.deleteById(id);
    }
}
