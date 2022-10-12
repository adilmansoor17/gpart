package com.example.gpark.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class slots {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public boolean saveToDB() {

        Map<String, Object> fuser = new HashMap<>();
        fuser.put("map", this.map);
        fuser.put("sensorID", this.sensorID);
        fuser.put("slot", this.slot);
        fuser.put("status",this.status);
        fuser.put("user",this.user);

        db.collection("slots")
                .add(fuser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

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

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    String slot;
    String map;
    String status;
    String sensorID;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String user;

    public slots(String slot, String map, String status, String sensorID) {
        this.slot = slot;
        this.map = map;
        this.status = status;
        this.sensorID = sensorID;
        this.user="";
    }
}
