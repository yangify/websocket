package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {

    private Long id;
    private String name;
    private List<Group> groups;
    private List<Notification> notificationsSent;
    private List<ReceiverNotification> notificationsReceived;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "users")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    @OneToMany(mappedBy = "sender")
    public List<Notification> getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(List<Notification> notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    @OneToMany(mappedBy = "primaryKey.notification")
    public List<ReceiverNotification> getNotificationsReceived() {
        return notificationsReceived;
    }

    public void setNotificationsReceived(List<ReceiverNotification> receiverNotifications) {
        this.notificationsReceived = receiverNotifications;
    }
}
