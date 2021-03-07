package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByDateBetween(Date start, Date end);

    List<Notification> findAllByDateBefore(Date end);

    List<Notification> findAllByDateAfter(Date start);
}
