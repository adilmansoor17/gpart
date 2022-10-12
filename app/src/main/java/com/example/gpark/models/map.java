package com.example.gpark.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class map {
    String slot, location, col, id, row, type;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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

    public map(String slot, String location, String col, String row, String type) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.slot = slot;
        this.location = location;
        this.col = col;
        this.id = String.valueOf(timestamp.getTime());
        this.row = row;
        this.type = type;

    }

    public map() {
    }

    public boolean saveToDB() {


        Map<String, Object> fuser = new HashMap<>();
        fuser.put("col", this.col);
        fuser.put("id", this.id);
        fuser.put("location", this.location);
        fuser.put("row",this.row);
        fuser.put("slot",this.slot);
        fuser.put("type",this.type);

        db.collection("map")
                .add(fuser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                        saveSlots();

                    }


                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding to cloud firestore", e);

                    }
                });
        return true;

    }

    private void saveSlots() {

        for(int i=0;i<Integer.parseInt(this.slot);i++){

            slots newSlot = new slots(String.valueOf(i+1), this.location, "false",String.valueOf((1000+i)) );
            newSlot.saveToDB();

            sensor newSensor = new sensor("false", String.valueOf(i+1),String.valueOf((1000+i)) );
            newSensor.saveToDB();

        }

    }


}
