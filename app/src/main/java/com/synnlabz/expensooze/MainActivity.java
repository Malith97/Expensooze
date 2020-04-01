package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {
    private List pieData;
    private PieChartView pieChartView;
    private DatabaseHelper db;
    private int Month;
    int[] allColors = new int[] {Color.rgb(255,200,63),Color.rgb(255,8,12),Color.rgb(0, 255, 148),Color.rgb(223, 214, 255),Color.rgb(255, 19, 19),Color.rgb(0, 76, 255)};
    String[] month_name = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        pieChartView = findViewById(R.id.chart);
        pieData = new ArrayList<SliceValue>();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        Month = Integer.valueOf(dateFormat.format(date));
        getlist();
    }

    @Override
    public void onBackPressed() {

    }

    public void getlist(){
        Cursor cursor = db.getdata("Select Name,SUM(amount) As amm from Record,Catagory where Record.cid=Catagory.cid AND month ="+String.valueOf(Month)+" GROUP BY Name ORDER BY Name");
        pieData.clear();
        int i =0;
        while (cursor.isAfterLast() == false) {
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Amm = cursor.getString(cursor.getColumnIndex("amm"));
            //Log.i("Test",Name);
            //PieEntry entry=new PieEntry(Integer.valueOf(Amm),Name);
            pieData.add(new SliceValue(Integer.valueOf(Amm), allColors[i]).setLabel(Name));
            i++;
            cursor.moveToNext();
        }
        view_piechart();
    }

    public void view_piechart(){

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setHasLabelsOutside(true).setValueLabelsTextColor(Color.BLACK);
        pieChartData.setHasCenterCircle(true).setCenterText1(month_name[Month-1]).setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#212A51"));
        pieChartView.setPieChartData(pieChartData);

    }

    public void goToAddBill(View view) {
        startActivity(new Intent(MainActivity.this,AddBill.class));
    }

    public void goToViewBill(View view) {
        startActivity(new Intent(MainActivity.this,ViewBill.class));
    }

    public void goToReports(View view) {
        startActivity(new Intent(MainActivity.this,Reports .class));
    }

    public void goToAddCategory(View view) {
        startActivity(new Intent(MainActivity.this,AddCategory .class));
    }
}
