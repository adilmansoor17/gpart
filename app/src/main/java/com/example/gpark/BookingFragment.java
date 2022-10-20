package com.example.gpark;

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

import com.example.gpark.models.slots;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    RecyclerView recview;
    ArrayList<slots> datalist;
    FirebaseFirestore db;
    myMapadapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference().getDatabase();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public BookingFragment() {
        // Required empty public constructor
    }


    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
        return inflater.inflate(R.layout.fragment_booking_form, container, false);
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
        adapter = new myMapadapter(datalist);
        recview.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("slots").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            slots obj = d.toObject(slots.class);

                            mDatabase.child("private").child(obj.getSensorID()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    String currentStatus = "---";
                                    String currentStatusJson = "---";
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    } else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));

                                        try {
                                            JSONObject Json = new JSONObject(String.valueOf(task.getResult().getValue()));
                                            currentStatusJson = Json.get("status").toString();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if (currentStatusJson.equals("free")) {
                                            currentStatus = "false";
                                            obj.setStatus(currentStatus);
                                        } else if (currentStatusJson.equals("occupied")) {
                                            currentStatus = "true";
                                            obj.setStatus(currentStatus);
                                        }
                                    }
                                    datalist.add(obj);
                                    adapter.notifyDataSetChanged();

                                }
                            });


                        }
                    }
                });


    }

}