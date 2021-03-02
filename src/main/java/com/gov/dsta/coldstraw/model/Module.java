package com.gov.dsta.coldstraw.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.List;

@Entity
public class Module {

    private Long id;
    private String name;
    private URL baseUrl;
    private List<Notification> notifications;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public URL getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    @OneToMany(mappedBy = "module")
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
