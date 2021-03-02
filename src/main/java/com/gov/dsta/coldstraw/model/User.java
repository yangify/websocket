package com.gov.dsta.coldstraw.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "\"user\"")
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

    @ManyToMany()
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
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
