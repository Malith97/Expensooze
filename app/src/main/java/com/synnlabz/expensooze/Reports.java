package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class Reports extends AppCompatActivity {

    ImageButton previous , next;
    private List pieData;
    private List barData;
    private PieChartView pieChartView;
    private ColumnChartView columnChartView;
    private DatabaseHelper db;
    private int Month;
    private TextView monthtxtview,totview;
    int[] allColors = new int[] {Color.rgb(255,200,63),Color.rgb(255,8,12),Color.rgb(0, 255, 148),Color.rgb(223, 214, 255),Color.rgb(255, 19, 19),Color.rgb(0, 76, 255)};
    String[] month_name = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        previous = (ImageButton)findViewById(R.id.lastMonth);
        next = (ImageButton)findViewById(R.id.nextMonth);
        db = new DatabaseHelper(this);
        pieChartView = findViewById(R.id.chart2);
        columnChartView = findViewById(R.id.barchart2);
        monthtxtview = (TextView) findViewById(R.id.monthtxt1);
        totview = (TextView) findViewById(R.id.totpie);
        pieData = new ArrayList<SliceValue>();
        barData = new ArrayList<Column>();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        Month = Integer.valueOf(dateFormat.format(date));
        update_month();
        getlist();
        getlist2();

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
    }

    public void backToHome(View view) {
        startActivity(new Intent(Reports.this,MainActivity.class));
    }
    private void update_month(){
        monthtxtview.setText(month_name[Month-1]);
    }

    public void getlist(){
        Cursor cursor = db.getdata("Select Name,SUM(amount) As amm from Record,Catagory where Record.cid=Catagory.cid AND month ="+String.valueOf(Month)+" GROUP BY Name ORDER BY Name");
        pieData.clear();
        int i =0;
        int tot =0;
        while (cursor.isAfterLast() == false) {
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Amm = cursor.getString(cursor.getColumnIndex("amm"));
            Log.i("Test",Name);
            tot += Integer.valueOf(Amm);
            //PieEntry entry=new PieEntry(Integer.valueOf(Amm),Name);
            pieData.add(new SliceValue(Integer.valueOf(Amm), allColors[i]).setLabel(Name));
            i++;
            cursor.moveToNext();
        }
        totview.setText(String.valueOf(tot));
        view_piechart();
    }

    public void getlist2(){
        Cursor cursor = db.getdata("Select month,SUM(amount) As amm from Record GROUP BY month ORDER BY month");
        barData.clear();
        int i =0;
        List<SubcolumnValue> values;
        values = new ArrayList<SubcolumnValue>();
        while (cursor.isAfterLast() == false) {
            String monthname = cursor.getString(cursor.getColumnIndex("month"));
            String Amm = cursor.getString(cursor.getColumnIndex("amm"));
            Log.i("Test2",monthname +" "+Amm);
            //PieEntry entry=new PieEntry(Integer.valueOf(Amm),Name);
            values.add(new SubcolumnValue(Integer.valueOf(Amm), ChartUtils.COLOR_GREEN));
            //barData.add(new Column(new SubcolumnValue(Integer.valueOf(Amm), ChartUtils.COLOR_GREEN)));
            i++;
            cursor.moveToNext();
        }
        barData.add(values);
        view_barchart();
    }

    public void view_piechart(){

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setHasLabelsOutside(true).setValueLabelsTextColor(Color.BLACK);
        pieChartData.setHasCenterCircle(true).setCenterText1(month_name[Month-1]).setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#212A51"));
        pieChartView.setPieChartData(pieChartData);
    }

    public void view_barchart(){

        ColumnChartData columnChartData = new ColumnChartData();
        columnChartView.setColumnChartData(columnChartData);

    }
}
