package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@JsonIgnoreProperties(value = {"groups"}, allowSetters = true)
public class Notification implements Serializable {

    private UUID id;
    private Module module;
    private User sender;
    private Set<NotificationReceiver> receivers;
    private Set<Group> groups;
    private String message;
    private Date date = new Date();

    public Notification() {
        this.receivers = new HashSet<>();
        this.groups = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotNull
    @ManyToOne()
    @JsonIgnoreProperties(value = {"notifications"}, allowSetters = true)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @NotNull
    @ManyToOne()
    @JsonIgnoreProperties(value = {"id", "groups", "notificationsSent", "notificationsReceived"}, allowSetters = true)
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @OneToMany(mappedBy = "notification")
    @JsonIgnoreProperties(value = {"id", "notification", "deleted"}, allowSetters = true)
    public Set<NotificationReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<NotificationReceiver> receivers) {
        this.receivers = receivers;
    }

    @ManyToMany()
    @JoinTable(
            name = "notification_group",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIgnoreProperties(value = {"notifications", "users"}, allowSetters = true)
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

    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notification)) return false;
        Notification notification = (Notification) object;
        return this.id.equals(notification.getId());
    }
}
