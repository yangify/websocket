package com.gov.dsta.coldstraw.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"group\"")
@JsonIgnoreProperties(value = "id", allowGetters = true)
public class Group {

    private UUID id;
    private String name;
    private Set<User> users;
    private Set<Notification> notifications;

    public Group() {
        this.users = new HashSet<>();
        this.notifications = new HashSet<>();
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
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany()
    @JsonIgnoreProperties({"groups", "notificationsSent", "notificationsReceived"})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (users.contains(user)) return;
        this.users.add(user);
        user.addGroup(this);
    }

    public void removeUser(User user) {
        if (!users.contains(user)) return;
        this.users.remove(user);
        user.removeGroup(this);
    }

    @ManyToMany(mappedBy = "groups")
    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Group && ((Group) object).getId().equals(this.id);
    }
}
