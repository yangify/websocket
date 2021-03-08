package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
public class Notification implements Serializable {

    private UUID id;
    private Module module;
    private User sender;
    private Set<ReceiverNotification> receivers;
    private Set<Group> groups;
    private String message;
    private Date date = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

//    @NotNull
    @ManyToOne()
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

//    @NotNull
    @ManyToOne()
    @JsonIgnoreProperties({"id", "groups", "notificationsSent", "notificationsReceived"})
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @OneToMany(mappedBy = "primaryKey.receiver")
    public Set<ReceiverNotification> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<ReceiverNotification> receiverNotifications) {
        this.receivers = receiverNotifications;
    }

    @ManyToMany()
    @JoinTable(
            name = "group_notification",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    public Set<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
