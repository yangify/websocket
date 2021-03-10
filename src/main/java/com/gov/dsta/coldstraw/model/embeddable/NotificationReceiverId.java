package com.gov.dsta.coldstraw.model.embeddable;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class NotificationReceiverId implements Serializable {

    private UUID receiverId;
    private UUID notificationId;

    public NotificationReceiverId() { }

    public UUID getReceiverId() {
        return receiverId;
    }

    public NotificationReceiverId setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public NotificationReceiverId setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NotificationReceiverId that = (NotificationReceiverId) o;
        return Objects.equals(receiverId, that.receiverId) &&
                Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, notificationId);
    }
}
