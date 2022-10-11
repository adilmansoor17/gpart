package com.example.gpark.models;

public class sensor {
    String status, slot, id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public sensor(String status, String slot, String id) {
        this.status = status;
        this.slot = slot;
        this.id = id;
    }

    public sensor() {

    }
}
