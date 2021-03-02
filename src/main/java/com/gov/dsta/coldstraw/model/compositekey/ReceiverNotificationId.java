package com.gov.dsta.coldstraw.model.compositekey;

import com.gov.dsta.coldstraw.model.Notification;
import com.gov.dsta.coldstraw.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ReceiverNotificationId implements Serializable {

    private User receiver;
    private Notification notification;

    @ManyToOne(cascade = CascadeType.ALL)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Notification getNotification() {
        return  notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
