package com.gov.dsta.coldstraw.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Notification implements Serializable {

    private Long id;
    private Module module;
    private User sender;
    private List<ReceiverNotification> receivers;
    private List<Group> groups;
    private String message;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @OneToMany(mappedBy = "primaryKey.receiver")
    public List<ReceiverNotification> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ReceiverNotification> receiverNotifications) {
        this.receivers = receiverNotifications;
    }

    @ManyToMany()
    @JoinTable(
            name = "group_notification",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    public List<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Group> groups) {
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
