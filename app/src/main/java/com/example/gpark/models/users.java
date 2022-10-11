package com.example.gpark.models;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gpark.LoginActivity;
import com.example.gpark.RegistrationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class users {

    public String email;
    public String fullname;
    public String phonenumber;
    public String registrationnumber;

    public String id;

    public String statuss;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public String getName(){
        return this.fullname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhone(){
        return this.phonenumber;
    }

    public String getreg(){
        return this.registrationnumber;
    }

    public users(){}

    public users(
            String email,
            String fullname,
            String phonenumber,
            String registrationnumber
    ){
        this.statuss="VIP";
        mAuth = FirebaseAuth.getInstance();
        this.email=email;
        this.fullname=fullname;
        this.phonenumber=phonenumber;
        this.registrationnumber=registrationnumber;

        this.savetoDb();

    }

    public void assignUser(
            String email,
            String fullname,
            String phonenumber,
            String registrationnumber
    ){
        this.statuss="VIP";
        this.email=email;
        this.fullname=fullname;
        this.phonenumber=phonenumber;
        this.registrationnumber=registrationnumber;
    }

    public void savetoDb(){


        Map<String, Object> fuser = new HashMap<>();
        fuser.put("fullname", this.fullname);
        fuser.put("phonenumber", this.phonenumber);
        fuser.put("email", this.email);
        fuser.put("registrationnumber",this.registrationnumber);

        db.collection("users")
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


    }

    public void getOneUser(String id){
        DocumentReference document=db.collection("users").document(id);

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    assignUser(documentSnapshot.get("email").toString(),documentSnapshot.get("fullname").toString(),documentSnapshot.get("phonenumber").toString(),documentSnapshot.get("registrationnumber").toString());
                }else{

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.w("TAG", "Error getting user", e);

            }
        });

    }

}
