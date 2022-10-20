package com.example.gpark.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sensor {
    String status, slot, id;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference().getDatabase();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


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
    public String getSlotStatus(String SensorID){

        mDatabase.child("private").child(SensorID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        return "false";
    }
    public Map getSlotObject(){
        String newStatusFree;
        if(this.status=="free"){
            newStatusFree="free";
        }else{
            newStatusFree="occupied";
        }

        Map<String, Object> fuser = new HashMap<>();
        fuser.put("distance", 100);
        fuser.put("status",newStatusFree);

        return fuser;
    }

    public Map getSlotInverseObject(){

        String newStatusFree;
        if(this.status=="free"){
            newStatusFree="occupied";
        }else{
            newStatusFree="free";
        }

        Map<String, Object> fuser = new HashMap<>();
        fuser.put("distance", 100);
        fuser.put("status",newStatusFree);

        return fuser;
    }

    public void saveRealtimeSlotFree(boolean status){
        if(!status){
            mDatabase.child("private").child(this.slot).setValue(this.getSlotObject());
        }else{
            mDatabase.child("private").child(this.slot).setValue(this.getSlotInverseObject());
        }

    }
    public boolean saveToDB() {

        Map<String, Object> fuser = new HashMap<>();
        fuser.put("id", this.id);
        fuser.put("slot", this.slot);
        fuser.put("status", this.status);

        db.collection("sensor")
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
}
