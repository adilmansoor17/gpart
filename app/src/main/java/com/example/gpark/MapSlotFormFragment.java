package com.example.gpark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gpark.models.map;

public class MapSlotFormFragment extends Fragment {

    private EditText slot, location, col, id, row,  status, sensorID;
    private String type = null;

    public MapSlotFormFragment() {
    }

    public static MapSlotFormFragment newInstance(String param1, String param2) {
        MapSlotFormFragment fragment = new MapSlotFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_slot_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        location = view.findViewById(R.id.location);
        row = view.findViewById(R.id.row);
        col = view.findViewById(R.id.col);
        slot = view.findViewById(R.id.slots);

        view.findViewById(R.id.button_slotSave).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                map newMap=new map(slot.getText().toString().trim(), location.getText().toString().trim(), col.getText().toString().trim(),
                         row.getText().toString().trim(), type.trim());
                    if(newMap.saveToDB()){
                        row.setText("");
                        location.setText("");
                        col.setText("");
                        slot.setText("");
                    }
            }
        });

        view.findViewById(R.id.vip).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                type="VIP";
            }
        });

        view.findViewById(R.id.user).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                type="user";
            }
        });
    }



}