package com.example.gpark;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gpark.models.booking;
import com.example.gpark.models.slots;
import com.example.gpark.models.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment {

    RecyclerView recview;
    ArrayList<booking> datalist;
    FirebaseFirestore db;
    myBookingDetailadapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference().getDatabase();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.populateList();

        view.findViewById(R.id.reload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                populateList();
            }
        });

    }

    public void populateList() {

        recview = (RecyclerView) getView().findViewById(R.id.recviewmap);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        datalist = new ArrayList<>();
        adapter = new myBookingDetailadapter(datalist);
        recview.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("booking").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            booking obj = d.toObject(booking.class);


                            Log.wtf("user of idd",obj.getUser());

                            db.collection("users")
                                    .whereEqualTo("email", obj.getUser())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            String name="Bhatti";

                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    Log.wtf(TAG, document.getId() + " => " + document.getData().get("fullname"));
                                                    name=document.getData().get("fullname").toString();
                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }

                                            obj.setUser(name);

                                            datalist.add(obj);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });





                        }
                    }
                });


    }

}