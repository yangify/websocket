package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    @JsonIgnoreProperties({"module", "receivers", "groups"})
    public Notification getNotification() {
        return this.notification;
    }

    public void setNotification(Notification notification) {
        this.notification= notification;
    }

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"id", "groups", "notificationsSent", "notificationsReceived"})
    public User getReceiver() {
        return this.receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
}
