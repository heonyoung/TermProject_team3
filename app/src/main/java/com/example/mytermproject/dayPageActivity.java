package com.example.mytermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class dayPageActivity extends AppCompatActivity {
    FoodData foodData = new FoodData(dayPageActivity.this, 1);
    String curDate;
    DBHelper dbHelper;
    String inputName;
    String curType;
    public Button morningBtn,lunchBtn,dinnerBtn,addBtn,editBtn,mapbutton;
    public ImageButton backBtn;
    public TextView aggKcal,aggCarbs,aggProtein,aggFat,aggNatrium,textView;
    public EditText aggCOMMENTS;
    public EditText foodText;
    public MealAdapter mealAdapter;
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    ArrayList<Meal> mealList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_page);
        curDate = intent.getStringExtra("curDate");
        curType = intent.getStringExtra("curType");
        TextView tv = (TextView) findViewById(R.id.date);
        tv.setText(curDate);
        backBtn=findViewById(R.id.backBtn);
        textView=findViewById(R.id.textView);
        progressBar=findViewById(R.id.progressBar);
        morningBtn = findViewById(R.id.morningBtn);
        lunchBtn = findViewById(R.id.lunchBtn);
        dinnerBtn = findViewById(R.id.dinnerBtn);
        aggKcal=findViewById(R.id.kcalSum);
        aggCarbs=findViewById(R.id.carbsSum);
        aggProtein=findViewById(R.id.proteinSum);
        aggFat=findViewById(R.id.fatSum);
        aggNatrium=findViewById(R.id.naSum);
        foodText=findViewById(R.id.foodName);
        addBtn=findViewById(R.id.addBtn);
        dbHelper = new DBHelper(dayPageActivity.this, 1);
        recyclerView = findViewById(R.id.myRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mealList=dbHelper.getResult(curDate,curType);
        mealAdapter=new MealAdapter();
        mealAdapter.setArrayData(mealList);
        recyclerView.setAdapter(mealAdapter);
        editBtn=findViewById(R.id.editBtn);
        Meal DayMealResult=dbHelper.getDayAggregation(curDate);

        aggKcal.setText(Integer.toString(DayMealResult.kcal));
        aggCarbs.setText(Integer.toString(DayMealResult.carbs));
        aggProtein.setText(Integer.toString(DayMealResult.protein));
        aggFat.setText(Integer.toString(DayMealResult.fat));
        aggNatrium.setText(Integer.toString(DayMealResult.nat));
        ReInit();
        morningBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                morningBtn.setSelected(true);
                lunchBtn.setSelected(false);
                dinnerBtn.setSelected(false);
                curType = "아침";
                addBtn.setVisibility(View.VISIBLE);
                foodText.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(),RecyclerView.VERTICAL,false));
                double tmp = (double)DayMealResult.kcal*100/4000;
                textView.setText(Double.toString(tmp)+"%");
                progressBar.setProgress((int)tmp);
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                ReInit();
            }
        });
        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morningBtn.setSelected(false);
                lunchBtn.setSelected(true);
                dinnerBtn.setSelected(false);
                curType = "점심";
                addBtn.setVisibility(View.VISIBLE);
                foodText.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(),RecyclerView.VERTICAL,false));
                double tmp = (double)DayMealResult.kcal*100/4000;
                textView.setText(Double.toString(tmp)+"%");
                progressBar.setProgress((int)tmp);
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                ReInit();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                v.getContext().startActivities(new Intent[]{intent});
                finish();
            }
        });

        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morningBtn.setSelected(false);
                lunchBtn.setSelected(false);
                dinnerBtn.setSelected(true);
                curType = "저녁";
                addBtn.setVisibility(View.VISIBLE);
                foodText.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(),RecyclerView.VERTICAL,false));
                double tmp = (double)DayMealResult.kcal*100/4000;
                textView.setText(Double.toString(tmp)+"%");
                progressBar.setProgress((int)tmp);
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                ReInit();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodData.insert2();
                Intent intent2 = new Intent(v.getContext(), SubActivity.class);
                inputName = String.valueOf(foodText.getText());
                intent2.putExtra("foodName",inputName);
                intent2.putExtra("curDate",curDate);
                intent2.putExtra("curType",curType);
                startActivity(intent2);
                ReInit();
                finish();
            }
        });

    }

    public void ReInit(){
        Meal DayMealResult=dbHelper.getDayAggregation(curDate);
        aggKcal.setText(Integer.toString(DayMealResult.kcal));
        aggCarbs.setText(Integer.toString(DayMealResult.carbs));
        aggProtein.setText(Integer.toString(DayMealResult.protein));
        aggFat.setText(Integer.toString(DayMealResult.fat));
        aggNatrium.setText(Integer.toString(DayMealResult.nat));
        mealList=dbHelper.getResult(curDate,curType);
        mealAdapter.setArrayData(mealList);
        recyclerView.setAdapter(mealAdapter);
        double tmp = (double)DayMealResult.kcal*100/4000;
        textView.setText(Double.toString(tmp)+"%"+"(하루평균 성인 권장 칼로리 4000kcal기준)");
        progressBar.setProgress((int)tmp);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }
}
