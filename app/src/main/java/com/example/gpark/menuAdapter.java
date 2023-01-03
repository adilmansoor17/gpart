package com.example.gpark;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpark.models.users;

import java.util.ArrayList;

public class menuAdapter extends ArrayAdapter
{

    public menuAdapter(@NonNull Context context, ArrayList<users> user) {
        super(context, 0,user);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int pos, View view, ViewGroup parent){
        if(view==null){
            view=LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_faculty,parent, false
            );
        }

        TextView textView = view.findViewById(R.id.text11);
        users user = (users) getItem(pos);

        textView.setText(user.getName());

        return view;
    }

}
