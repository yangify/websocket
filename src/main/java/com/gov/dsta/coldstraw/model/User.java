package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@JsonIgnoreProperties("id")
public class User implements Serializable {

    private UUID id;
    private String name;
    private Set<Group> groups = new HashSet<>();
    private Set<Notification> notificationsSent = new HashSet<>();
    private Set<ReceiverNotification> notificationsReceived = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        if (groups.contains(group)) return;
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
