package com.example.mytermproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealViewHolder> {
    private ArrayList<Meal> arrayList;
    public MealAdapter(){
        arrayList=new ArrayList<>();
    }
    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.mealrecyclerview,parent,false);
        MealViewHolder viewholer = new MealViewHolder(context,view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal text=arrayList.get(position);
        holder.Id=text.Id;
        holder.cnt=text.count;
        holder.comment=text.COMMENTS;
        holder.name.setText(text.Name);
        holder.curDate= text.CurDate;
        holder.curType=text.CurType;
        holder.kcal.setText(Integer.toString(text.kcal*text.count));
        holder.carbs.setText(Integer.toString(text.carbs*text.count));
        holder.protein.setText(Integer.toString(text.protein*text.count));
        holder.fat.setText(Integer.toString(text.fat*text.count));
        holder.na.setText(Integer.toString(text.nat*text.count));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void setArrayData(ArrayList<Meal> list){
        arrayList = list;
    }
}
