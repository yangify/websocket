package com.gov.dsta.coldstraw.model;

public class Count {
    private Long total;
    private Long unread;
    private Long read;

    public Long getTotal() {
        return total;
    }

    public Count setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getUnread() {
        return unread;
    }

    public Count setUnread(Long unread) {
        this.unread = unread;
        return this;
    }

    public Long getRead() {
        return read;
    }

    public Count setRead(Long read) {
        this.read = read;
        return this;
    }
}
