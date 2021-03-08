package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
public class Notification implements Serializable {

    private UUID id;
    private Module module;
    private User sender;
    private Set<User> receivers;
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

//    @NotNull
    @ManyToOne()
    @JsonIgnoreProperties({"notifications"})
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (module == null) return;
        this.module = module;
        module.addNotification(this);
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

    @JoinTable(
            name = "notification_receiver",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany()
    public Set<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<User> receivers) {
        this.receivers = receivers;
    }

    @JoinTable(
            name = "notification_group",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @ManyToMany()
    @JsonIgnoreProperties({"notifications", "users"})
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
