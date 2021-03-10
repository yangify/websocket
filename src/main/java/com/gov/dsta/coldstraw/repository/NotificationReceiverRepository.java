package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.model.User;
import com.gov.dsta.coldstraw.model.embeddable.NotificationReceiverId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationReceiverRepository extends CrudRepository<NotificationReceiver, NotificationReceiverId>, PagingAndSortingRepository<NotificationReceiver, NotificationReceiverId> {

    NotificationReceiver findNotificationReceiverByNotificationAndReceiver(Notification notification, User receiver);

    List<NotificationReceiver> findNotificationReceiverByReceiver(User receiver);

    List<NotificationReceiver> findNotificationReceiverByReceiver(User receiver, Pageable pageable);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateAfter(User receiver, Date start);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateAfter(User receiver, Date start, Pageable pageable);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateBefore(User receiver, Date end);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateBefore(User receiver, Date end, Pageable pageable);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateBetween(User receiver, Date start, Date end);

    List<NotificationReceiver> findNotificationReceiversByReceiverAndNotification_DateBetween(User receiver, Date start, Date end, Pageable pageable);

    List<NotificationReceiver> findNotificationReceiverByReceiverAndRead(User receiver, Boolean read);
}
