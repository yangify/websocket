package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gov.dsta.coldstraw.model.embeddable.NotificationReceiverId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class NotificationReceiver implements Serializable {

    private NotificationReceiverId id;
    private User receiver;
    private Notification notification;
    private boolean isRead = false;
    private boolean isDeleted = false;

    public NotificationReceiver() {
        this.id = new NotificationReceiverId();
    }

    @EmbeddedId
    public NotificationReceiverId getId() {
        return id;
    }

    public void setId(NotificationReceiverId id) {
        this.id = id;
    }

    @ManyToOne()
    @MapsId("notificationId")
    @JsonIgnoreProperties(value = {"id", "receivers", "groups"}, allowSetters = true)
    public Notification getNotification() {
        return this.notification;
    }

    public NotificationReceiver setNotification(Notification notification) {
        this.notification= notification;
        this.id.setNotificationId(notification.getId());
        return this;
    }

    @ManyToOne()
    @MapsId("receiverId")
    @JsonIgnoreProperties(value = {"id", "groups", "notificationsSent", "notificationsReceived"}, allowSetters = true)
    public User getReceiver() {
        return this.receiver;
    }

    public NotificationReceiver setReceiver(User receiver) {
        this.receiver = receiver;
        this.id.setReceiverId(receiver.getId());
        return this;
    }

    @NotNull
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    @NotNull
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof NotificationReceiver)) return false;
        NotificationReceiver that = (NotificationReceiver) object;
        return Objects.equals(this.receiver.getName(), that.getReceiver().getName());
    }

    @Override
    public int hashCode() {
        return this.receiver.getName().hashCode();
    }
}
