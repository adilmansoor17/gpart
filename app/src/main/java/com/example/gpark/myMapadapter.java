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
import com.example.gpark.models.map;
import com.example.gpark.models.slots;
import com.example.gpark.models.users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class myMapadapter extends RecyclerView.Adapter<myMapadapter.myviewholder>
{

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<slots> datalist;
    private String currentUserId=mAuth.getCurrentUser().getUid();

    public myMapadapter(ArrayList<slots> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_map_user,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        String status=datalist.get(position).getStatus();
        int id;
        int visibility_bk;
        int visibility_fr;
        Log.wtf("my status tag", status);
        if(status.toLowerCase().equals("true")){
            status="Got";
            id=R.drawable.red_car;
            visibility_bk=View.GONE;
            visibility_fr=View.GONE;
        }else if(status.toLowerCase().equals("false")){
            status="Free";
            id=R.drawable.gree_car;
            visibility_bk=View.VISIBLE;
            visibility_fr=View.GONE;
        }else{
            status="Booked";
            id=R.drawable.blue_car;
            visibility_bk=View.GONE;
            visibility_fr=View.VISIBLE;
        }

        holder.bk.setVisibility(visibility_bk);
        holder.fr.setVisibility(visibility_fr);

        holder.t1.setText(datalist.get(position).getSlot());
        holder.t2.setText(status);
        holder.t3.setText(datalist.get(position).getSensorID());
        holder.t4.setText(datalist.get(position).getMap());
        holder.IV.setImageResource(id);

        holder.bk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.wtf("my status click", "book clicked");

                booking newBooking=new booking(datalist.get(position).getSlot(), "true",datalist.get(position).getSensorID());
                newBooking.saveToDB();

                holder.bk.setVisibility(View.GONE);
                holder.fr.setVisibility(View.VISIBLE);
                holder.t2.setText("Booked");
                holder.IV.setImageResource(R.drawable.blue_car);

                slots newslot=new slots(datalist.get(position).getSlot(), datalist.get(position).getMap(),
                        datalist.get(position).getStatus(), datalist.get(position).getSensorID());

                newslot.bookSlot();
            }
        });

        holder.fr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.wtf("my status click", "free clicked");
                holder.fr.setVisibility(View.GONE);
                holder.bk.setVisibility(View.VISIBLE);
                holder.t2.setText("Free");
                holder.IV.setImageResource(R.drawable.gree_car);

                slots newslot=new slots(datalist.get(position).getSlot(), datalist.get(position).getMap(),
                        datalist.get(position).getStatus(), datalist.get(position).getSensorID());

                booking newBooking=new booking(datalist.get(position).getSlot(), "false",datalist.get(position).getSensorID());
                newBooking.removeBooking();

                newslot.freeSlot();

            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1,t2, t3, t4;
        ImageView IV;
        Button bk, fr;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            IV=itemView.findViewById(R.id.imageview);
            bk=itemView.findViewById(R.id.btn_book);
            fr=itemView.findViewById(R.id.btn_free);

        }
    }
}
