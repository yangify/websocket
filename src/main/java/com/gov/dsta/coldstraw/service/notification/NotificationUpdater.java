package com.gov.dsta.coldstraw.service.notification;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NotificationUpdater {

    public void updateReadStatus(Notification originalNotification, Notification newNotification) {
        Set<NotificationReceiver> originalNRs = originalNotification.getReceivers();
        Set<NotificationReceiver> newNRs = newNotification.getReceivers();

        newNRs.forEach(newNR -> {
            if (!originalNRs.contains(newNR)) return;
            originalNRs.stream()
                    .filter(originalNR -> originalNR.equals(newNR))
                    .findFirst()
                    .ifPresent(originalNR -> originalNR.setRead(newNR.isRead()));
        });
    }
}
