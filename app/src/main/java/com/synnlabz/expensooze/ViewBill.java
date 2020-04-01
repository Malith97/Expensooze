package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewBill extends AppCompatActivity {

    private ImageButton previous , next;
    private TextView tottxt,monthtxtview;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private List<Bill> dataList;
    private List<String> dataListID;
    private int total,Month;
    String[] month_name = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        total =0;
        setContentView(R.layout.activity_view_bill);
        dataList = new ArrayList<Bill>();
        dataListID = new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_bill);
        previous = (ImageButton)findViewById(R.id.lastMonth);
        next = (ImageButton)findViewById(R.id.nextMonth);
        tottxt = (TextView) findViewById(R.id.tot);
        monthtxtview = (TextView) findViewById(R.id.monthtxt);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Month>1){
                    Month--;
                    update_month();
                    getlist();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"There are no records",Toast.LENGTH_SHORT).show();
                if(Month<12){
                    Month++;
                    update_month();
                    getlist();
                }
            }
        });
        db = new DatabaseHelper(this);
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        Month = Integer.valueOf(dateFormat.format(date));
        update_month();
        getlist();
    }

    private void update_month(){
        monthtxtview.setText(month_name[Month-1]);
    }
    public void getlist(){
        Cursor cursor = db.getdata("Select rid,Name,year,month,day,amount,remark from Record,Catagory where Record.cid=Catagory.cid AND month ="+String.valueOf(Month));
        dataList.clear();
        dataListID.clear();
        total = 0;
        if(!((cursor != null) && (cursor.getCount() > 0))){
            Toast.makeText(getApplicationContext(),"There are no records",Toast.LENGTH_SHORT).show();
        }
        while (cursor.isAfterLast() == false) {
            String rid = cursor.getString(cursor.getColumnIndex("rid"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            String month = cursor.getString(cursor.getColumnIndex("month"));
            String day = cursor.getString(cursor.getColumnIndex("day"));
            String amount = cursor.getString(cursor.getColumnIndex("amount"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Bill bill = new Bill(rid, Name, year, month, day, amount, remark);
            total += Integer.valueOf(amount);
            dataList.add(bill);
            dataListID.add(rid);
            Log.i("temp", "Name = " + Name + " Amount = " + amount);
            cursor.moveToNext();
        }
        tottxt.setText(String.valueOf(total));
        new Recycleview_config().setConfig(recyclerView, ViewBill.this, dataList, dataListID);
    }


    public void backToHome(View view) {
        startActivity(new Intent(ViewBill.this,MainActivity.class));
    }
}
