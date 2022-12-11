package com.example.mytermproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Item> arrayList;
    String curType,curDate;
    public Adapter(String curdate,String curtype){
        arrayList=new ArrayList<>();
        curType=curtype;
        curDate=curdate;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.recyclerview,parent,false);
        ViewHolder viewholer = new ViewHolder(context,view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item text=arrayList.get(position);
        holder.name.setText(text.Name);
        holder.curDate=curDate;
        holder.curType=curType;
        holder.kcal.setText(Integer.toString(text.kcal));
        holder.carbs.setText(Integer.toString(text.carbs));
        holder.protein.setText(Integer.toString(text.protein));
        holder.fat.setText(Integer.toString(text.fat));
        holder.na.setText(Integer.toString(text.nat));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void setArrayData(ArrayList<Item> list){
        arrayList = list;
    }
}
