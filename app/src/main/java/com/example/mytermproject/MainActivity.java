package com.example.mytermproject;

import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //String curDate,curType;
    public CalendarView calendarView;
    public Button changeBtn;
    public TextView textView;
    //public TextView daySummary;//,textViewper,textViewtex;
    public DBHelper dbHelper;
    public FoodData foodData;
    public ProgressBar progressBar;

    String curyear,curmonth,curday;
    Double per;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        //curDate = intent.getStringExtra("curDate");
        //curType = intent.getStringExtra("curType");
        //Meal DayMealResult=dbHelper.getDayAggregation(curDate);
        calendarView = findViewById(R.id.calendarView);
        changeBtn = findViewById(R.id.changeBtn);
        textView = findViewById(R.id.textView3);
        //daySummary = findViewById(R.id.daySummaryText);
        //textViewper = findViewById(R.id.textView6);
        //textViewtex = findViewById(R.id.textView5);
        //daySummary.setText("일간 총 칼로리:"+Integer.toString(DayMealResult.kcal));
        dbHelper=new DBHelper(MainActivity.this,1);
        dbHelper.CreateMydata();
        curcalculate();
        textView.setText(curyear +"/"+ curmonth +"/"+ curday+"기준 1주일간 평균 섭취 칼로리는\n"+Double.toString(per) +"kcal 입니다.");

        //progressBar=findViewById(R.id.prg);
        //progressBar.setProgress(DayMealResult.kcal/4000);
        //textViewper.setText(Integer.toString(DayMealResult.kcal/4000*100)+"%");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                changeBtn.setVisibility(VISIBLE);
                //daySummary.setVisibility(view.VISIBLE);
                moveToDay(year, month, dayOfMonth);
            }
        });
    }

    public void moveToDay(int year,int month,int dayOfMonth){
        Meal meal= dbHelper.getDayAggregation(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dayPageActivity.class);
                intent.putExtra("curDate", String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                startActivity(intent);
            }
        });
    }
    private  void createDatabase(String Name){

    }

    public void curcalculate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        curyear = yearFormat.format(currentTime);
        curmonth = monthFormat.format(currentTime);
        curday = dayFormat.format(currentTime);

        double sumkcal = 0;
        int yeartmp = Integer.parseInt(curyear);
        int monthtmp = Integer.parseInt(curmonth)-1;
        int dayOfMonthtmp = Integer.parseInt(curday);
        for(int i = 0;i<7;i++){
            if ((dayOfMonthtmp) == 0) {
                if (monthtmp == 0) {//넘어갔는데 12월인 경우 (ex.1월 3일)
                    monthtmp = 11;
                    dayOfMonthtmp = 31;
                    yeartmp = yeartmp - 1;
                } else if (monthtmp == 1 || monthtmp == 3 || monthtmp == 5 || monthtmp == 7 || monthtmp == 8 || monthtmp == 10) {//전달이 31일
                    monthtmp = monthtmp - 1;
                    dayOfMonthtmp = 31;
                } else if (monthtmp == 2) {//전달이 2월
                    monthtmp = monthtmp - 1;
                    dayOfMonthtmp = 28;
                    if ((monthtmp % 4 == 0) && (monthtmp % 100 != 0) || (monthtmp % 400 == 0)) {//윤년계산
                        dayOfMonthtmp = 29;
                    }
                } else if (monthtmp == 4 || monthtmp == 6 || monthtmp == 9 || monthtmp == 11) {//전달이 30일
                    monthtmp = monthtmp - 1;
                    dayOfMonthtmp = 30;
                }
            }

            Meal DayMealResult = dbHelper.getDayAggregation(String.format("%d / %d / %d", yeartmp, monthtmp + 1, dayOfMonthtmp));
            sumkcal += DayMealResult.kcal;
            dayOfMonthtmp -= 1;
        }
        double per2 = sumkcal/7;
        per = Double.parseDouble(String.format("%.2f",per2));

    }
}