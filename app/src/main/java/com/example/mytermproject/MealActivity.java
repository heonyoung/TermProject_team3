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
        if(Place != "?????? ??????") PlaceName.setText("?????? ??????: "+Place);
        else PlaceName.setText("???????????? ????????? ??????????????????");
        /*if(imgView.()!=null){
            foodImage.setVisibility(View.INVISIBLE);
        }*/
        String tmp = getCacheDir() + "/" + Integer.toString(id);   // ?????? ???????????? ???????????? ?????? ????????? ??????
        Bitmap tmp2 = BitmapFactory.decodeFile(tmp);
        if(tmp2!=null){
            foodImage.setVisibility(View.INVISIBLE);
        }
        comments.setText(comment);
        foodCnt.setText(Integer.toString(cnt));
        kcalText.setText("?????????\n\n"+kcal);
        carbsText.setText("????????????\n\n"+carbs);
        proteinText.setText("?????????\n\n"+protein);
        fatText.setText("??????\n\n"+fat);
        naText.setText("?????????\n\n"+na);

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("????????????","edit");
                dbHelper.EditComments(id,Integer.parseInt(foodCnt.getText().toString()),comments.getText().toString());
                Intent intent = new Intent(v.getContext(),dayPageActivity.class); //?????????
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                startActivity(intent); //???????????? ??????
                finish();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.Delete(id);
                Intent intent = new Intent(v.getContext(),dayPageActivity.class); //?????????
                intent.putExtra("curDate",curDate);
                intent.putExtra("curType",curType);
                startActivity(intent); //???????????? ??????
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
            String imgpath = getCacheDir() + "/" + Integer.toString(id);   // ?????? ???????????? ???????????? ?????? ????????? ??????
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            Log.d("imgpath",imgpath);
            Log.d("imgpath",Integer.toString(id));
            imgView.setImageBitmap(bm);   // ?????? ???????????? ????????? ???????????? ??????????????? ???
            Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
        }
    }

    public void bt1(View view) {    // ????????? ?????? ????????? ????????? ????????? ?????? ????????? ??????
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // ?????????
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    imgView.setImageBitmap(imgBitmap);    // ????????? ????????? ??????????????? ???
                    instream.close();   // ????????? ????????????
                    saveBitmapToJpeg(imgBitmap);    // ?????? ???????????? ??????
                    Toast.makeText(getApplicationContext(), "?????? ???????????? ??????", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "?????? ???????????? ??????", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {   // ????????? ????????? ?????? ???????????? ??????
        File tempFile = new File(getCacheDir(), Integer.toString(id));    // ?????? ????????? ?????? ??????
        try {
            tempFile.createNewFile();   // ???????????? ??? ????????? ????????????
            FileOutputStream out = new FileOutputStream(tempFile);  // ????????? ??? ??? ?????? ???????????? ????????????
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress ????????? ????????? ???????????? ???????????? ????????????
            out.close();    // ????????? ????????????
            Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
            foodImage.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
        }
    }

}