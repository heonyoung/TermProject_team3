package com.example.mytermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class MealActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(MealActivity.this,1);
    public TextView DateText,TypeText,kcalText,carbsText,proteinText,fatText,naText,FoodnamText,PlaceName;
    public ImageButton foodImage;
    public ImageView imgView;
    public Button delBtn,EditBtn,mapBtn;
    public String curDate,curType,name,comment;
    public EditText comments,foodCnt;
    public  String kcal,carbs,protein,fat,na,Place;
    public int id,cnt;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Intent intent = getIntent();
        name=intent.getStringExtra("foodName");
        curType=intent.getStringExtra("curType");
        curDate=intent.getStringExtra("curDate");
        carbs=intent.getStringExtra("carbs");
        protein=intent.getStringExtra("protein");
        kcal=intent.getStringExtra("kcal");
        fat=intent.getStringExtra("fat");
        na=intent.getStringExtra("na");
        id=intent.getIntExtra("ID",1);
        cnt= intent.getIntExtra("COUNT",0);
        comment=intent.getStringExtra("comment");
        Place=dbHelper.GetPlace(id);
        foodImage=findViewById(R.id.foodImg);

        imgView=findViewById(R.id.imgView);
        TypeText=findViewById(R.id.type);
        delBtn=findViewById(R.id.delBtn);
        EditBtn=findViewById(R.id.editBtn);
        FoodnamText=findViewById(R.id.foodName);
        DateText = findViewById(R.id.date);
        kcalText=findViewById(R.id.kcalText);
        carbsText=findViewById(R.id.carbsText);
        PlaceName=findViewById(R.id.PlaceName);
        proteinText=findViewById(R.id.proteinText);
        fatText=findViewById(R.id.fatText);
        naText=findViewById(R.id.naText);
        comments=findViewById(R.id.commentText);
        foodCnt=findViewById(R.id.foodCnt);
        FoodnamText.setText(name);
        mapBtn = findViewById(R.id.Btnmap);
        DateText.setText(curDate);
        TypeText.setText(curType);
        if(Place != "현재 위치") PlaceName.setText("식사 장소: "+Place);
        else PlaceName.setText("지도에서 위치를 입력해주세요");
        /*if(imgView.()!=null){
            foodImage.setVisibility(View.INVISIBLE);
        }*/
        String tmp = getCacheDir() + "/" + Integer.toString(id);   // 내부 저장소에 저장되어 있는 이미지 경로
        Bitmap tmp2 = BitmapFactory.decodeFile(tmp);
        if(tmp2!=null){
            foodImage.setVisibility(View.INVISIBLE);
        }
        comments.setText(comment);
        foodCnt.setText(Integer.toString(cnt));
        kcalText.setText("칼로리\n\n"+kcal);
        carbsText.setText("탄수화물\n\n"+carbs);
        proteinText.setText("단백질\n\n"+protein);
        fatText.setText("지방\n\n"+fat);
        naText.setText("나트륨\n\n"+na);

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("버튼클릭","edit");
                dbHelper.EditComments(id,Integer.parseInt(foodCnt.getText().toString()),comments.getText().toString());
                Intent intent = new Intent(v.getContext(),dayPageActivity.class); //인텐트
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                startActivity(intent); //액티비티 열기
                finish();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.Delete(id);
                Intent intent = new Intent(v.getContext(),dayPageActivity.class); //인텐트
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                startActivity(intent); //액티비티 열기
                finish();
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),MapsActivity.class);
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                intent.putExtra("ID",id);
                v.getContext().startActivities(new Intent[]{intent});
            }
        });
        try {
            String imgpath = getCacheDir() + "/" + Integer.toString(id);   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            Log.d("imgpath",imgpath);
            Log.d("imgpath",Integer.toString(id));
            imgView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void bt1(View view) {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    imgView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    instream.close();   // 스트림 닫아주기
                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getCacheDir(), Integer.toString(id));    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
            foodImage.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

}