package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PieChartView pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(35, Color.rgb(255, 213, 79)));
        pieData.add(new SliceValue(10, Color.rgb(3, 169, 244 )));
        pieData.add(new SliceValue(25, Color.rgb(76, 175, 80)));
        pieData.add(new SliceValue(40, Color.rgb(244, 67, 54 )));

        PieChartData pieChartData = new PieChartData(pieData);
        //pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("MARCH").setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#212A51"));
        pieChartView.setPieChartData(pieChartData);
    }
}
