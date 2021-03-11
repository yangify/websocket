package com.gov.dsta.coldstraw.controller;

import com.gov.dsta.coldstraw.assembler.NotificationReceiverAssembler;
import com.gov.dsta.coldstraw.exception.notification.NotificationNotFoundException;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import com.gov.dsta.coldstraw.service.NotificationReceiverService;
import com.gov.dsta.coldstraw.service.WebsocketService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/receivers")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationReceiverController {

    private final NotificationReceiverService notificationReceiverService;
    private final WebsocketService websocketService;
    private final NotificationReceiverAssembler notificationReceiverAssembler;

    public NotificationReceiverController(NotificationReceiverService notificationReceiverService,
                                          WebsocketService websocketService,
                                          NotificationReceiverAssembler notificationReceiverAssembler) {

        this.notificationReceiverService = notificationReceiverService;
        this.websocketService = websocketService;
        this.notificationReceiverAssembler = notificationReceiverAssembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<NotificationReceiver>> getNotificationReceivers(@RequestParam(required = false) Integer page,
                                                                                       @RequestParam(required = false) Integer size,
                                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        List<NotificationReceiver> notificationReceivers = notificationReceiverService.getNotificationReceivers();

        if (requireDate(start, end) && requirePagination(page, size))
            notificationReceivers = notificationReceiverService.getNotificationReceiversByDateAndPage(start, end, page, size);

        if (requireDate(start, end))
            notificationReceivers = notificationReceiverService.getNotificationReceiversByDate(start, end, null);

        if (requirePagination(page, size))
            notificationReceivers = notificationReceiverService.getNotificationReceiversByPage(page, size);

        return notificationReceiverAssembler.toCollectionModel(notificationReceivers);
    }

    public CollectionModel<EntityModel<NotificationReceiver>> getNotificationReceivers() {
        List<NotificationReceiver> notificationReceivers = notificationReceiverService.getNotificationReceivers();
        return notificationReceiverAssembler.toCollectionModel(notificationReceivers);
    }

    @GetMapping("/{notificationId}")
    public EntityModel<NotificationReceiver> getNotificationReceiver(@PathVariable UUID notificationId) {
        try {
            NotificationReceiver notificationReceiver = notificationReceiverService.getNotificationReceiver(notificationId);
            return notificationReceiverAssembler.toModel(notificationReceiver);
        } catch (NotificationNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<EntityModel<NotificationReceiver>> updateNotificationReceiver(@PathVariable UUID notificationId,
                                                           @RequestBody NotificationReceiver notificationReceiver) {
        NotificationReceiver savedNotificationReceiver = notificationReceiverService.updateNotificationReceiver(notificationId, notificationReceiver);
        websocketService.publishCount();
        EntityModel<NotificationReceiver> notificationReceiverModel = notificationReceiverAssembler.toModel(savedNotificationReceiver);
        return ResponseEntity
                .created(notificationReceiverModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(notificationReceiverModel);
    }

    @PutMapping()
    public ResponseEntity<Void> readAllNotificationReceiver() {
        notificationReceiverService.readAllNotificationReceiver();
        websocketService.publishCount();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllNotificationReceiver() {
        notificationReceiverService.deleteAllNotificationReceiver();
        websocketService.publishCount();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotificationReceiver(@PathVariable UUID notificationId) {
        notificationReceiverService.deleteNotificationReceiver(notificationId);
        websocketService.publishCount();
        return ResponseEntity.noContent().build();
    }

    private boolean requirePagination(Integer page, Integer size) {
        return page != null && size != null;
    }

    private boolean requireDate(Date startDate, Date endDate) {
        return startDate != null || endDate != null;
    }
}
