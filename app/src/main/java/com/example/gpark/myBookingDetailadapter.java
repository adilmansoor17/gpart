package com.example.gpark;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpark.models.booking;
import com.example.gpark.models.slots;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myBookingDetailadapter extends RecyclerView.Adapter<myBookingDetailadapter.myviewholder>
{

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<booking> datalist;
    private String currentUserId=mAuth.getCurrentUser().getUid();

    public myBookingDetailadapter(ArrayList<booking> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_booking_user,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        String status=datalist.get(position).getStatus();
        int id;

        Log.wtf("my status tag", status);

        if(status.toLowerCase().equals("true")){
            id=R.drawable.tick;
        }else if(status.toLowerCase().equals("false")){
            id=R.drawable.cross;
        }else{
            id=R.drawable.info;
        }

        holder.t1.setText(datalist.get(position).getUser().toString().split("8")[0]);
        holder.t2.setText(datalist.get(position).getSlot());
        holder.t4.setText(datalist.get(position).getTime().toString().split(" ")[0]);
        holder.IV.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1,t2, t3, t4;
        ImageView IV;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            IV=itemView.findViewById(R.id.imageview);

        }
    }
}
