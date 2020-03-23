package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddBill extends AppCompatActivity {

    private AppCompatSpinner spinnerCategory, spinnerType;
    private Long type;
    private Long category;

    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        save = (ImageButton)findViewById(R.id.saveBill);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

            }
        });

        spinnerType = (AppCompatSpinner) findViewById(R.id.addBill);
        spinnerType.setPrompt(getResources().getString(R.string.addBill_prompt));
        //spinnerType.setAdapter(new TypeSpinnerAdapter(this, android.R.layout.simple_dropdown_item_1line));
        //type = ((AcctType) spinnerType.getAdapter().getItem(0)).getId();
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //code eka gahapan methana adapter eken ganna tikata
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //type = Long.valueOf(-1);
            }
        });
    }

    public void backToHome(View view) {
        finish();
    }
}
