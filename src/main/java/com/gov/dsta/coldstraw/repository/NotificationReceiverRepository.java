package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationReceiverRepository extends CrudRepository<NotificationReceiver, UUID> {

    List<NotificationReceiver> findNotificationReceiverByReceiver(User receiver);

    List<NotificationReceiver> findNotificationReceiverByReceiverAndRead(User receiver, Boolean read);
}
