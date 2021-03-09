package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class NotificationReceiver implements Serializable {

    private UUID id;
    private User receiver;
    private Notification notification;
    private boolean isRead = false;
    private boolean isDeleted = false;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "notification_id")
    @JsonIgnoreProperties({"id", "receivers", "groups"})
    public Notification getNotification() {
        return this.notification;
    }

    public NotificationReceiver setNotification(Notification notification) {
        this.notification= notification;
        return this;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"id", "groups", "notificationsSent", "notificationsReceived"})
    public User getReceiver() {
        return this.receiver;
    }

    public NotificationReceiver setReceiver(User receiver) {
        this.receiver = receiver;
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
