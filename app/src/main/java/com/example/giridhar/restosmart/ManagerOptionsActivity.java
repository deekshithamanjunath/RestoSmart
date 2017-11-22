package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerOptionsActivity extends AppCompatActivity implements View.OnClickListener {
Button btAddDish,btEditDish,btDeleteDish;
String selectedCategory,selectedSubCategory,tablename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_options);

        btAddDish =(Button)findViewById(R.id.button4);
        btEditDish=(Button)findViewById(R.id.button5);
        btDeleteDish =(Button)findViewById(R.id.button6);

        Intent i = getIntent();
        tablename =i.getStringExtra("tablename");
        selectedCategory =i.getStringExtra("category");
        selectedSubCategory =i.getStringExtra("subcategory");

    }

    @Override
    protected void onStart() {
        super.onStart();
        btAddDish.setOnClickListener(this);
        btEditDish.setOnClickListener(this);
        btDeleteDish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
       switch ((v.getId()))
       {
           case R.id.button4:
               Intent i =new Intent(ManagerOptionsActivity.this,AddNewDishActivity.class);
               i.putExtra("tablename",tablename);
               i.putExtra("category",selectedCategory);
               i.putExtra("subcategory",selectedSubCategory);
               startActivity(i);
               break;
           case R.id.button5:
               Intent navigate = new Intent(ManagerOptionsActivity.this,EditDishActivity.class);
               navigate.putExtra("tablename",tablename);
               navigate.putExtra("category",selectedCategory);
               navigate.putExtra("subcategory",selectedSubCategory);
               startActivity(navigate);
               break;
           case R.id.button6:
               Intent navigateTo = new Intent(ManagerOptionsActivity.this,RemoveDishActivity.class);
               navigateTo.putExtra("tablename",tablename);
               navigateTo.putExtra("category",selectedCategory);
               navigateTo.putExtra("subcategory",selectedSubCategory);
               startActivity(navigateTo);
               break;
       }

    }
}
