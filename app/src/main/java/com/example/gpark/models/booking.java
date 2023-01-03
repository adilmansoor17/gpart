package com.example.gpark.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class booking {
    String slot, status, time, user, id;
    ArrayList allBookings= new ArrayList<booking>();

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public booking() {
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

    public booking(String slot, String status, String id, String User) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.slot = slot;
        this.status = status;
        this.time = timestamp.toString();
        this.user = User;
        this.id = id;
    }

    public booking(String slot, String status, String id) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        this.slot = slot;
        this.status = status;
        this.time = timestamp.toString();
        this.user = mAuth.getCurrentUser().getEmail();
        this.id = id;
    }

    public void removeBooking() {
        CollectionReference collection = db.collection("booking");

        collection.whereEqualTo("id", this.id)
                .whereEqualTo("slot", this.slot)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<Object, String> map = new HashMap<>();
                                map.put("status", "false");
                                collection.document(document.getId()).set(map, SetOptions.merge());
                                Log.wtf("my removeBooking click", "updated removeBooking successfulyy");
                            }
                        }
                    }
                });
    }

    public boolean saveToDB() {


        Map<String, Object> fuser = new HashMap<>();
        fuser.put("slot", this.slot);
        fuser.put("id", this.id);
        fuser.put("status", this.status);
        fuser.put("time",this.time);
        fuser.put("user",this.user);


        db.collection("booking")
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

    public void getBookings(){

        CollectionReference collection = db.collection("booking");

        collection.whereEqualTo("user", this.user)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                booking book = document.toObject(booking.class);
                                allBookings.add(book);
                                Log.wtf("my getBookings click", "fetched successfulyy");
                            }
                        }
                    }
                });
    }

    public ArrayList getAllBookings() {
        return allBookings;
    }

    public void setAllBookings(ArrayList allBookings) {
        this.allBookings = allBookings;
    }
}
