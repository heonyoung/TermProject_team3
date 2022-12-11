package com.example.mytermproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Person Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists MEALS(ID integer primary key AUTOINCREMENT,CurDate TEXT,CurType TEXT,NAME TEXT, KCAL INT,CARBS INT,PROTEIN INT, FAT INT, NATRIUM INT,comment text,la text,lo text,count int,PlaceName Text,imgPath text)");
    }

    // Person Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MEALS");
        onCreate(db);
    }
    public void CreateMydata(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE if not exists MEALS(ID integer primary key AUTOINCREMENT,CurDate TEXT,CurType TEXT,NAME TEXT, KCAL INT,CARBS INT,PROTEIN INT, FAT INT, NATRIUM INT,comment text,la text,lo text,count int,PlaceName Text,imgPath text)");
    }
    // Person Table 데이터 입력
    public void insert(String curDate, String curType,String Name,int kcal,int carbs,int protein,int fat,int natrium) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MEALS(CurDate,CurType,NAME,KCAL,CARBS,PROTEIN, FAT, NATRIUM,comment,count,PlaceName) VALUES('" + curDate + "', '" + curType + "', '" + Name + "' , " + kcal+ ", " + carbs+ ", " + protein+ ", " + fat+ ", " + natrium+ " , '평가를 입력해주세요', 1,'현재 위치' )");
        db.close();
    }
    // Person Table 데이터 수정
    public void Update(String curDate, String curType,int kcal,int carbs,int protein,int fat,int natrium) {
        SQLiteDatabase db = getWritableDatabase();
    }

    // Person Table 데이터 삭제s
    public void Delete(int Id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE from MEALS WHERE Id="+Id+"");
    }

    // Person Table 조회
    public ArrayList<Meal> getResult(String date, String type) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("CREATE TABLE if not exists MEALS(ID integer primary key AUTOINCREMENT,CurDate TEXT,CurType TEXT,NAME TEXT, KCAL INT,CARBS INT,PROTEIN INT, FAT INT, NATRIUM INT,comment text,la text,lo text,count int,PlaceName Text,imgPath text)");
        ArrayList<Meal> result = new ArrayList<Meal>();
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEALS WHERE curDate ='"+date+"'and curType ='"+type+"'", null);
        while (cursor.moveToNext()) {
            Meal meal =new Meal(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8),cursor.getString(9),cursor.getInt(0),cursor.getInt(12));
            result.add(meal);
            Log.d("내 식단",meal.Name);
        }
        return result;
    }
    public Meal getDayAggregation(String date) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("CREATE TABLE if not exists MEALS(ID integer primary key AUTOINCREMENT,CurDate TEXT,CurType TEXT,NAME TEXT, KCAL INT,CARBS INT,PROTEIN INT, FAT INT, NATRIUM INT,comment text,la text,lo text,count int,PlaceName Text,imgPath text)");
        Meal result;
       int kcal=0,carbs=0,protein=0,fat=0,nat=0,Id=0;
        Cursor cursor = db.rawQuery("SELECT * FROM MEALS WHERE curDate ='"+date+"'", null);
        while (cursor.moveToNext()) {
            kcal+=cursor.getInt(4)*cursor.getInt(12);
            carbs+=cursor.getInt(5)*cursor.getInt(12);
            protein+=cursor.getInt(6)*cursor.getInt(12);
            fat+=cursor.getInt(7)*cursor.getInt(12);
            nat+=cursor.getInt(8)*cursor.getInt(12);
            Id=cursor.getInt(0);
        }
        result=new Meal("","","",kcal,carbs,protein,fat,nat,"",Id,0);
        return result;
    }
    public String GetPlace(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PlaceName FROM MEALS WHERE id = "+id+"", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }
    public void EditComments(int id,int cnt,String comment) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("update meals set comment = '"+comment+"' where Id="+id+"");
        db.execSQL("update meals set count = "+cnt+" where Id="+id+"");
        Log.d("Edit",Integer.toString(cnt));
        Log.d("Edit",comment);
    }

    public void EditPosition(int id,String la,String lo,String PlaceName) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("update meals set la = '"+la+"',lo = '"+lo+"', PlaceName = '"+PlaceName+"' where ID = "+id+"");
    }
    public String GetPositionla(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT la FROM MEALS WHERE id = "+id+"", null);
        cursor.moveToFirst();
        if(cursor.isNull(0)) {
            Log.d("tag", "이프");
            return "36.798870793287";
        }
        else {
            Log.d("tag", "엘스");
            return cursor.getString(0);
        }
    }
    public String GetPositionlo(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT lo FROM MEALS WHERE id = "+id+"", null);
        cursor.moveToFirst();
        if(cursor.isNull(0)) {
            return "127.15974055604";
        }
        else
            return cursor.getString(0);
    }

    public String GetComments(String date,String type){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Comments FROM MEALS WHERE curDate ='"+date+"' and curType='"+type+"'", null);
        if(cursor.moveToNext())
            return cursor.getString(0);
        else
            return "";
    }

}