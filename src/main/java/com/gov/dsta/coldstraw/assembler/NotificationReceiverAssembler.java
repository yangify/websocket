package com.gov.dsta.coldstraw.assembler;

import com.gov.dsta.coldstraw.controller.NotificationReceiverController;
import com.gov.dsta.coldstraw.model.NotificationReceiver;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NotificationReceiverAssembler implements RepresentationModelAssembler<NotificationReceiver, EntityModel<NotificationReceiver>> {

    @Override
    public EntityModel<NotificationReceiver> toModel(NotificationReceiver notificationReceiver) {
        return EntityModel.of(notificationReceiver,
                linkTo(methodOn(NotificationReceiverController.class).getNotificationReceiver(notificationReceiver.getNotification().getId())).withSelfRel(),
                linkTo(methodOn(NotificationReceiverController.class).getNotificationReceivers()).withRel("groups")
        );
    }

    public CollectionModel<EntityModel<NotificationReceiver>> toCollectionModel(List<NotificationReceiver> notificationReceivers) {
        List<EntityModel<NotificationReceiver>> notificationReceiversModel = notificationReceivers.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(notificationReceiversModel, linkTo(methodOn(NotificationReceiverController.class).getNotificationReceivers()).withSelfRel());

    }
}
