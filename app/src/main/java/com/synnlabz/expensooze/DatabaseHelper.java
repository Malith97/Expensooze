package com.synnlabz.expensooze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "expansooz.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Catagory(cid INTEGER primary key AUTOINCREMENT,Name text)");
        db.execSQL("Create table Record(rid INTEGER primary key AUTOINCREMENT,cid INTEGER,year INTEGER,month INTEGER,day INTEGER,amount INTEGER,remark text,FOREIGN KEY(cid) REFERENCES Catagory(cid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Record");
        db.execSQL("drop table if exists Catagory");
        onCreate(db);
    }

    public boolean insertCat(String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name",Name);

        long ins = db.insert("Catagory",null,contentValues);

        if (ins == -1) {
            return false;
        }else{
            return true;
        }
    }
    public boolean insertRec(String cid ,String year ,String month ,String day ,String amount ,String remark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("cid",cid);
        contentValues.put("year",year);
        contentValues.put("month",month);
        contentValues.put("day",day);
        contentValues.put("amount",amount);
        contentValues.put("remark",remark);
        long ins = db.insert("Record",null,contentValues);

        if (ins == -1) {
            return false;
        }else{
            return true;
        }
    }


    public Cursor getdata(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        return cursor;
    }

    public void DeleteCat(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Catagory","cid = ?",new String[]{id});
    }
    public void DeleteRec(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Record","rid = ?",new String[]{id});
    }

}
