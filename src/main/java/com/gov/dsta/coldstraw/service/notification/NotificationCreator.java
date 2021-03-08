package com.gov.dsta.coldstraw.service.notification;

import com.gov.dsta.coldstraw.model.*;
import com.gov.dsta.coldstraw.model.Module;
import com.gov.dsta.coldstraw.service.GroupService;
import com.gov.dsta.coldstraw.service.ModuleService;
import com.gov.dsta.coldstraw.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationCreator {

    private final GroupService groupService;
    private final ModuleService moduleService;
    private final UserService userService;

    public NotificationCreator(GroupService groupService, ModuleService moduleService, UserService userService) {
        this.groupService = groupService;
        this.moduleService = moduleService;
        this.userService = userService;
    }

    public void create(Notification notification) {
        craftModule(notification);
        craftSender(notification);
        craftGroups(notification);
        craftReceivers(notification);
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

    private void craftReceivers(Notification notification) {
        Set<NotificationReceiver> receivers = notification
                .getReceivers()
                .stream()
                .peek(notificationReceiver -> {
                    String receiverName = notificationReceiver.getReceiver().getName();
                    User receiver = userService.getUser(receiverName);
                    notificationReceiver.setReceiver(receiver);
                    notificationReceiver.setNotification(notification);
                })
                .collect(Collectors.toSet());
        notification.setReceivers(receivers);
    }
}
