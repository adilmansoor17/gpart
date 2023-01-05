package com.example.gpark;

import android.annotation.SuppressLint;
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
import com.example.gpark.models.sensor;
import com.example.gpark.models.slots;
import com.example.gpark.models.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment {

    RecyclerView recview, recview2, recview3;
    ArrayList<slots> datalist;
    ArrayList<sensor> datalistS, datalistS2, datalistS3;

    FirebaseFirestore db;
    mySensorAdapter adapterS, adapterS2, adapterS3;
    myMapadapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference().getDatabase();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


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
        String user_type = this.getArguments().getString("user_type");
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        this.populateList();
        this.populateRealTimeList();
//        view.findViewById(R.id.reload).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                populateRealTimeList();
//            }
//        });

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

                                        if(!obj.getStatus().equals("book")){
                                            if (currentStatusJson.equals("free")) {
                                                currentStatus = "false";
                                                obj.setStatus(currentStatus);
                                            } else if (currentStatusJson.equals("occupied")) {
                                                currentStatus = "true";
                                                obj.setStatus(currentStatus);
                                            }
                                            else{
                                                obj.setStatus("book");
                                            }
                                        }else{
                                            if (currentStatusJson.equals("occupied")) {
                                                currentStatus = "true";
                                                obj.setStatus(currentStatus);
                                            }
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

    public void populateRealTimeList(){


        mDatabase.child("private").addValueEventListener(
                new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            recview = (RecyclerView) getView().findViewById(R.id.recviewmap);
                            recview.setLayoutManager(new LinearLayoutManager(getContext()));

                            recview2 = (RecyclerView) getView().findViewById(R.id.recviewmap2);
                            recview2.setLayoutManager(new LinearLayoutManager(getContext()));

                            recview3 = (RecyclerView) getView().findViewById(R.id.recviewmap3);
                            recview3.setLayoutManager(new LinearLayoutManager(getContext()));


                            datalist = new ArrayList<>();
                            datalistS=new ArrayList<>();
                            datalistS2=new ArrayList<>();
                            datalistS3=new ArrayList<>();

                            adapterS = new mySensorAdapter(datalistS);
                            adapterS2 = new mySensorAdapter(datalistS2);
                            adapterS3= new mySensorAdapter(datalistS3);

                            recview.setAdapter(adapterS);
                            recview2.setAdapter(adapterS2);
                            recview3.setAdapter(adapterS3);



                            if(snapshot.hasChildren()){
                                long length=snapshot.getChildrenCount();
                                int i=0;
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    i++;
                                    String id=d.getKey();
                                    String status="---";
                                    String key=d.getKey();
                                    String slot="0";

                                    if(key.length()==5){
                                        slot= String.valueOf((d.getKey().toString()).charAt(4));

                                    }else if(key.length()==6){
                                        slot= String.valueOf(
                                                (Integer.parseInt(String.valueOf((d.getKey()).charAt(4)))*10)+
                                                        Integer.parseInt(String.valueOf((d.getKey()).charAt(5)))
                                        );
                                    }else if(key.length()==7){
                                        slot= String.valueOf(
                                                (Integer.parseInt(String.valueOf((d.getKey()).charAt(4)))*100)+
                                                        (Integer.parseInt(String.valueOf((d.getKey()).charAt(5)))*10)+
                                                        Integer.parseInt(String.valueOf((d.getKey()).charAt(6)))
                                        );
                                    }


                                    Object objO= d.getValue();

                                    try {
                                        JSONObject Json = new JSONObject(objO.toString());
                                        status = Json.get("status").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    sensor slotObj=new sensor(status, slot, id);

                                    if((i%2==0)&&(i%5!=0)){
                                        datalistS2.add(slotObj);
                                        adapterS2.notifyDataSetChanged();
                                    }else if((i%5==0)){
                                        datalistS3.add(slotObj);
                                        adapterS3.notifyDataSetChanged();
                                    }else{
                                        datalistS.add(slotObj);
                                        adapterS.notifyDataSetChanged();
                                    }



                                    Log.wtf("realTime data", objO.toString());
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

}




//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("private").child(obj.getSensorID());
//                            reference.addValueEventListener(new ValueEventListener() {
//
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        for (DataSnapshot snapshot1 : snapshot.getChildren()){
//
//        snapshot1.getValue().toString();
//
//        String currentStatus = "---";
//        String currentStatusJson = "---";
//
//        Log.d("firebase", snapshot1.getValue().toString());
//
//        try {
//        JSONObject Json = new JSONObject(snapshot1.getValue().toString());
//        currentStatusJson = Json.get("status").toString();
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//
//        if (currentStatusJson.equals("free")) {
//        currentStatus = "false";
//        } else if (currentStatusJson.equals("occupied")) {
//        currentStatus = "true";
//        }
//
//        obj.setStatus(currentStatus);
//        datalist.add(obj);
//        adapter.notifyDataSetChanged();
//
//
//        }
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//        });
