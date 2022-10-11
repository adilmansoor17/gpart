package com.example.gpark.models;

import java.util.ArrayList;

public class map {
    String slot, location, col, id, row, type;

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public map(String slot, String location, String col, String id, String row, String type) {
        this.slot = slot;
        this.location = location;
        this.col = col;
        this.id = id;
        this.row = row;
        this.type = type;
    }

    public map() {
    }
}
