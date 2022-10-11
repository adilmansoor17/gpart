package com.example.gpark.models;

public class history {
    String slot, status, time, user, id;

    public history() {
        this.slot = slot;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public history(String slot, String status, String time, String user, String id) {
        this.slot = slot;
        this.status = status;
        this.time = time;
        this.user = user;
        this.id = id;
    }
}
