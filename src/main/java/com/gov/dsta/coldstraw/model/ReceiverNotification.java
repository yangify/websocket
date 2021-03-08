//package com.gov.dsta.coldstraw.model;
//
//import com.gov.dsta.coldstraw.model.compositekey.ReceiverNotificationId;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//
//@Entity
//@AssociationOverrides({
//        @AssociationOverride(name = "primaryKey.notification", joinColumns = @JoinColumn(name = "notification_id")),
//        @AssociationOverride(name = "primaryKey.receiver", joinColumns = @JoinColumn(name = "receiver_id")) })
//public class ReceiverNotification implements Serializable {
//
//    private ReceiverNotificationId primaryKey = new ReceiverNotificationId();
//    private boolean isRead = false;
//    private boolean isDeleted = false;
//
//    @EmbeddedId
//    public ReceiverNotificationId getPrimaryKey() {
//        return primaryKey;
//    }
//
//    public void setPrimaryKey(ReceiverNotificationId primaryKey) {
//        this.primaryKey = primaryKey;
//    }
//
//    @Transient
//    public Notification getNotification() {
//        return getPrimaryKey().getNotification();
//    }
//
//    public void setNotification(Notification notification) {
//        getPrimaryKey().setNotification(notification);
//    }
//
//    @Transient
//    public User getReceiver() {
//        return getPrimaryKey().getReceiver();
//    }
//
//    public void setReceiver(User receiver) {
//        getPrimaryKey().setReceiver(receiver);
//    }
//
//    @NotNull
//    public boolean isRead() {
//        return isRead;
//    }
//
//    public void setRead(boolean isRead) {
//        this.isRead = isRead;
//    }
//
//    @NotNull
//    public boolean isDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(boolean isDeleted) {
//        this.isDeleted = isDeleted;
//    }
//}
