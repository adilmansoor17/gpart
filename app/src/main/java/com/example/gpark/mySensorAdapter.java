package com.example.gpark;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpark.models.sensor;
import com.example.gpark.models.users;

import java.util.ArrayList;

public class mySensorAdapter extends RecyclerView.Adapter<mySensorAdapter.myviewholder>
{
    ArrayList<sensor> datalist;

    public mySensorAdapter(ArrayList<sensor> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_map,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        String status=datalist.get(position).getStatus();
        int id;

        if(status.toLowerCase().equals("free")){
            id=R.drawable.gree_car;
        }else if(status.toLowerCase().equals("occupied")){
            id=R.drawable.red_car;
        }else{
            id=R.drawable.blue_car;
        }

        holder.t1.setText(datalist.get(position).getSlot());
        holder.IV.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1;
        ImageView IV;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.slotID);
            IV=itemView.findViewById(R.id.imageview);
        }
    }
}
