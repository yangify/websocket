package com.gov.dsta.coldstraw.service;

import com.gov.dsta.coldstraw.model.Count;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.repository.NotificationReceiverRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationReceiverService {

    private final UserService userService;
    private final NotificationReceiverRepository notificationReceiverRepository;

    public NotificationReceiverService(UserService userService, NotificationReceiverRepository notificationReceiverRepository) {
        this.userService = userService;
        this.notificationReceiverRepository = notificationReceiverRepository;
    }

    public Count getCount(UUID receiverId) {
        User receiver = userService.getUser(receiverId);
        return getCount(receiver);
    }

    public Count getCount(String receiverName) {
        User receiver = userService.getUser(receiverName);
        return getCount(receiver.getId());
    }

    public Count getCount(User receiver) {
        int total = notificationReceiverRepository.findNotificationReceiverByReceiver(receiver).size();
        int unread = notificationReceiverRepository.findNotificationReceiverByReceiverAndRead(receiver, false).size();
        int read = notificationReceiverRepository.findNotificationReceiverByReceiverAndRead(receiver, true).size();
        return new Count()
                .setTotal((long) total)
                .setUnread((long) unread)
                .setRead((long) read);
    }
}
