package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ViewBill extends AppCompatActivity {

    ImageButton previous , next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        previous = (ImageButton)findViewById(R.id.lastMonth);
        next = (ImageButton)findViewById(R.id.nextMonth);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"There are no previous records",Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"There are no records",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toToaddExpenses(View view) {
        startActivity(new Intent(ViewBill.this,AddBill.class));
    }

    public void backToHome(View view) {
        startActivity(new Intent(ViewBill.this,MainActivity.class));
    }
}
