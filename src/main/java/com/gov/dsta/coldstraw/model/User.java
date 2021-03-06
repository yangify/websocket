package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    private Long id;
    private String name;
    private Set<Group> groups = new HashSet<>();
    private Set<Notification> notificationsSent = new HashSet<>();
    private Set<ReceiverNotification> notificationsReceived = new HashSet<>();

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
    @JsonIgnoreProperties({"users", "notifications"})
    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.addUser(this);
    }

    public void removeGroup(Group group) {
        if (!groups.contains(group)) return;
        this.groups.remove(group);
        group.removeUser(this);
    }

    @OneToMany(mappedBy = "sender")
    public Set<Notification> getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(Set<Notification> notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    @OneToMany(mappedBy = "primaryKey.notification")
    public Set<ReceiverNotification> getNotificationsReceived() {
        return notificationsReceived;
    }

    public void setNotificationsReceived(Set<ReceiverNotification> receiverNotifications) {
        this.notificationsReceived = receiverNotifications;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof User && ((User) object).getId().equals(this.id);
    }
}
