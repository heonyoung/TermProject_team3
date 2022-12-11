package com.example.mytermproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealViewHolder extends RecyclerView.ViewHolder {
    public int Id;
    public TextView name;
    public TextView kcal;
    public TextView protein;
    public TextView carbs;
    public TextView fat;
    public TextView na;
    public ImageButton delButton;
    public String curDate,curType,comment;
    public int cnt;
    DBHelper dbHelper;
    ArrayList<Meal> mealList;
    MealAdapter mealAdapter=new MealAdapter();
    RecyclerView recyclerView;
    public MealViewHolder(Context context, View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.nameText);
        kcal=itemView.findViewById(R.id.kcalText);
        carbs=itemView.findViewById(R.id.carbsText);
        protein=itemView.findViewById(R.id.proteinText);
        fat=itemView.findViewById(R.id.fatText);
        na=itemView.findViewById(R.id.naText);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MealActivity.class);
                intent.putExtra("curDate", curDate);
                intent.putExtra("curType", curType);
                intent.putExtra("kcal", kcal.getText().toString());
                intent.putExtra("carbs",carbs.getText().toString());
                intent.putExtra("foodName",name.getText().toString());
                intent.putExtra("protein", protein.getText().toString());
                intent.putExtra("fat",fat.getText().toString());
                intent.putExtra("na",na.getText().toString());
                intent.putExtra("ID",Id);
                intent.putExtra("COUNT",cnt);
                intent.putExtra("comment",comment);
                v.getContext().startActivities(new Intent[]{intent});
            }
        });
    }

}
