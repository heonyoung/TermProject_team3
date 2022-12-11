package com.example.mytermproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {
    FoodData foodData = new FoodData(SubActivity.this, 1);
    Adapter adapter;
    String SearchName,curType,curDate;
    DBHelper dbHelper = new DBHelper(SubActivity.this,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        SearchName=intent.getStringExtra("foodName");
        curDate=intent.getStringExtra("curDate");
        curType=intent.getStringExtra("curType");
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1);
        ArrayList<Item> list=foodData.getResult(SearchName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter=new Adapter(curDate,curType);
        adapter.setArrayData(list);
        recyclerView.setAdapter(adapter);
    }
}