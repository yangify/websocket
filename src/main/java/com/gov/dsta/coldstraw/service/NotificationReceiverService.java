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

    public List<NotificationReceiver> getNotificationReceiversByDateAndPage(Date start, Date end,
                                                                            Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "notification.date"));
        return getNotificationReceiversByDate(start, end, pageable);
    }

    public List<NotificationReceiver> getNotificationReceiversByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "notification.date"));
        return getNotificationReceivers(pageable);
    }

    public List<NotificationReceiver> getNotificationReceiversByDate(Date start, Date end, Pageable pageable) {
        if (start == null)                  return getNotificationReceiversBefore(end, pageable);
        if (end == null)                    return getNotificationReceiversAfter(start, pageable);
        return getNotificationReceiversBetween(start, end, pageable);
    }

    public List<NotificationReceiver> getNotificationReceivers(Pageable pageable) {
        if (pageable == null) return getNotificationReceivers();
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByReceiver(receiver, pageable);
    }

    public List<NotificationReceiver> getNotificationReceivers() {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByReceiver(receiver);
    }

    public List<NotificationReceiver> getNotificationReceiversBefore(Date end, Pageable pageable) {
        if (pageable == null) return getNotificationReceiversBefore(end);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBefore(receiver, end, pageable);
    }

    public List<NotificationReceiver> getNotificationReceiversBefore(Date end) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBefore(receiver, end);
    }

    public List<NotificationReceiver> getNotificationReceiversAfter(Date start, Pageable pageable) {
        if (pageable == null) return getNotificationReceiversAfter(start);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateAfter(receiver, start, pageable);
    }

    public List<NotificationReceiver> getNotificationReceiversAfter(Date start) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateAfter(receiver, start);
    }

    public List<NotificationReceiver> getNotificationReceiversBetween(Date start, Date end, Pageable pageable) {
        if (pageable == null) return getNotificationReceiversBetween(start, end);
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBetween(receiver, start, end, pageable);
    }

    public List<NotificationReceiver> getNotificationReceiversBetween(Date start, Date end) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiversByReceiverAndNotification_DateBetween(receiver, start, end);
    }

    public NotificationReceiver getNotificationReceiver(Notification notification) {
        User receiver = userService.getUser(username);
        return notificationReceiverRepository.findNotificationReceiverByNotificationAndReceiver(notification, receiver);
    }

    public NotificationReceiver getNotificationReceiver(UUID notificationId) {
        User receiver = userService.getUser(username);
        NotificationReceiverId id = new NotificationReceiverId(receiver.getId(), notificationId);
        return notificationReceiverRepository
                .findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }

    public NotificationReceiver updateNotificationReceiver(UUID notificationReceiverId, NotificationReceiver notificationReceiver) {
        NotificationReceiver ogNotificationReceiver = getNotificationReceiver(notificationReceiverId);
        ogNotificationReceiver.setRead(notificationReceiver.isRead());
        return notificationReceiverRepository.save(ogNotificationReceiver);
    }

    public void readAllNotificationReceiver() {
        List<NotificationReceiver> notificationReceivers = getNotificationReceivers();
        notificationReceivers.forEach(notificationReceiver -> {
            notificationReceiver.setRead(true);
            notificationReceiverRepository.save(notificationReceiver);
        });
    }

    public void deleteAllNotificationReceiver() {
        notificationReceiverRepository.deleteAll();
    }

    public void deleteNotificationReceiver(UUID notificationId) {
        User receiver = userService.getUser(username);
        NotificationReceiverId id = new NotificationReceiverId(receiver.getId(), notificationId);
        notificationReceiverRepository.deleteById(id);
    }
}
