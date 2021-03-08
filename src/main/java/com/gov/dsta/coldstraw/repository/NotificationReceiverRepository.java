package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.model.compositekey.ReceiverNotificationId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationReceiverRepository extends CrudRepository<NotificationReceiver, ReceiverNotificationId> {
}
