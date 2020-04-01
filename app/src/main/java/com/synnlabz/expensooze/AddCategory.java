package com.synnlabz.expensooze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class AddCategory extends AppCompatActivity {

    DatabaseHelper db;
    List<String> dataList;
    ListView listView;
    EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        dataList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listnew);
        editTextName = (EditText)findViewById(R.id.editTextName);
        db = new DatabaseHelper(this);
        getlist();
    }

    public void getlist(){
        Cursor cursor = db.getdata("Select * from Catagory");
        dataList.clear();
        while (cursor.isAfterLast()==false){
            String id = cursor.getString(cursor.getColumnIndex("cid"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            dataList.add(Name);
            cursor.moveToNext();
        }
        //System.out.println(dataList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
    }

    public void backToHome(View view) {
        startActivity(new Intent(AddCategory.this,MainActivity.class));
    }

    public void AddCat(View view) {
        String word = editTextName.getText().toString();
        if(!(TextUtils.isEmpty(word))){
            Boolean insert =  db.insertCat(word);
            if (insert==true){
                editTextName.setText("");
                Toast.makeText(getApplicationContext(),"Insert is Successfull ",Toast.LENGTH_SHORT).show();
                getlist();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Please Enter the Field",Toast.LENGTH_SHORT).show();
        }
    }
}
