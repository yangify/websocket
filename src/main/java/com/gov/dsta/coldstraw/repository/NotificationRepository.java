package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
