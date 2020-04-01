package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddBill extends AppCompatActivity {

    private AppCompatSpinner spinnerType;
    private DatePickerDialog picker;
    private EditText eTextDate,eTextAmount,eTextRemark;
    private List<String> dataList;
    private List<String> dataListID;
    private DatabaseHelper db;

    private ImageButton save;
    private String Selected_Category;
    private int yr,mon,mday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        db = new DatabaseHelper(this);
        Selected_Category="0";
        yr = 0;mon = 0;mday = 0;
        dataList = new ArrayList<String>();
        dataListID = new ArrayList<String>();
        eTextDate=(EditText) findViewById(R.id.editDate);
        eTextAmount=(EditText) findViewById(R.id.editAmount);
        eTextRemark=(EditText) findViewById(R.id.editRemark);
        eTextDate.setInputType(InputType.TYPE_NULL);
        eTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddBill.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                yr = year;mon = (monthOfYear + 1);mday = dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        spinnerType = (AppCompatSpinner) findViewById(R.id.addBill);
        spinnerType.setPrompt(getResources().getString(R.string.addBill_prompt));
        loadSpinnerData();

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected_Category = dataListID.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Selected_Category = "1";
            }
        });
    }

    private void loadSpinnerData() {
        Cursor cursor = db.getdata("Select * from Catagory");
        dataList.clear();
        dataListID.clear();
        while (cursor.isAfterLast()==false){
            String id = cursor.getString(cursor.getColumnIndex("cid"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            dataListID.add(id);
            dataList.add(Name);
            cursor.moveToNext();
        }
        // Spinner Drop down elements


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataList);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerType.setAdapter(dataAdapter);
    }

    public void backToHome(View view) {
        finish();
    }

    public void AddRec(View view) {
        //Toast.makeText(getApplicationContext(),"Cat ID = "+Selected_Category,Toast.LENGTH_SHORT).show();
        String date = eTextDate.getText().toString();
        String Amount = eTextAmount.getText().toString();
        String Remark = eTextRemark.getText().toString();
        if(!((TextUtils.isEmpty(date))&&(TextUtils.isEmpty(Amount)))){
            Boolean insert =  db.insertRec(Selected_Category,String.valueOf(yr),String.valueOf(mon),String.valueOf(mday),Amount,Remark);
            if (insert==true){
                eTextDate.setText("");
                eTextAmount.setText("");
                eTextRemark.setText("");
                Toast.makeText(getApplicationContext(),"Insert is Successfull ",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Please Enter the Fields",Toast.LENGTH_SHORT).show();
        }
    }
}
