package com.example.gpark.models;

import java.util.ArrayList;

public class slots {
    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public slots() {
    }

    public slots(String slot, String map, String status) {
        this.slot = slot;
        this.map = map;
        this.status = status;
    }

    String slot, map, status;
}
