package com.gov.dsta.coldstraw.repository;

import com.gov.dsta.coldstraw.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, UUID>, PagingAndSortingRepository<Notification, UUID> {

    List<Notification> findAllByDateBetween(Date start, Date end);

    List<Notification> findAllByDateBetween(Date start, Date end, Pageable pageable);

    List<Notification> findAllByDateBefore(Date end);

    List<Notification> findAllByDateBefore(Date end, Pageable pageable);

    List<Notification> findAllByDateAfter(Date start);

    List<Notification> findAllByDateAfter(Date start, Pageable pageable);
}
