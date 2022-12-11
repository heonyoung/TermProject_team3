package com.example.mytermproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView kcal;
    public DBHelper dbHelper;
    public TextView protein;
    public TextView carbs;
    public TextView fat;
    public TextView na;
    public String curType;
    public String curDate;
    public ViewHolder(Context context, View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.nameText);
        kcal=itemView.findViewById(R.id.kcalText);
        carbs=itemView.findViewById(R.id.carbsText);
        protein=itemView.findViewById(R.id.proteinText);
        fat=itemView.findViewById(R.id.fatText);
        na=itemView.findViewById(R.id.naText);
        dbHelper=new DBHelper(context,1);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                Intent intent=new Intent(v.getContext(),dayPageActivity.class);
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                dbHelper.insert(curDate,curType,(String)name.getText(),Integer.parseInt((String)kcal.getText()),Integer.parseInt((String)carbs.getText()),Integer.parseInt((String)protein.getText()),Integer.parseInt((String)fat.getText()),Integer.parseInt((String)na.getText()));
                Log.d("실행됨",curDate+" "+curType);
                v.getContext().startActivities(new Intent[]{intent});
            }
        });
    }
}
