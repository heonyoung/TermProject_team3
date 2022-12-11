package com.example.mytermproject;
public class Meal {
    public String CurDate;
    public String CurType;
    public String Name;
    public String COMMENTS;
    public int kcal;
    public int carbs;
    public int protein;
    public int fat;
    public int nat;
    public int Id;
    public int count;
    public Meal(String curdate,String curtype,String n, int k, int c, int p, int f, int na,String s,int id,int cnt){
        CurDate=curdate;
        CurType=curtype;
        Name=n;
        kcal=k;
        carbs=c;
        protein=p;
        fat=f;
        nat=na;
        COMMENTS=s;
        Id=id;
        count=cnt;
    }
}
