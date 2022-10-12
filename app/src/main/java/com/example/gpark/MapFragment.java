package com.example.gpark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gpark.models.slots;
import com.example.gpark.models.users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment {

    RecyclerView recview;
    ArrayList<slots> datalist;
    FirebaseFirestore db;
    myMapadapter adapter;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.populateList();

    }

    public void populateList(){

        recview=(RecyclerView) getView().findViewById(R.id.recviewmap);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        datalist=new ArrayList<>();
        adapter=new myMapadapter(datalist);
        recview.setAdapter(adapter);

        db= FirebaseFirestore.getInstance();
        db.collection("slots").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            slots obj=d.toObject(slots.class);
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


    }

}