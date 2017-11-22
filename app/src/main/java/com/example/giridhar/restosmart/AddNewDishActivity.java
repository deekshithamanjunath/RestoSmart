package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewDishActivity extends AppCompatActivity implements View.OnClickListener {
EditText etdishname,etdishdesc,etdishprice;
Button btaddToFirebase;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
String tablename,categoryname,subcategoryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dish);

        etdishname=(EditText)findViewById(R.id.editText8);
        etdishdesc =(EditText)findViewById(R.id.editText9);
        etdishprice =(EditText)findViewById(R.id.editText11);
        btaddToFirebase =(Button)findViewById(R.id.button7);

        firebaseDatabase =FirebaseDatabase.getInstance();

        Intent i =getIntent();
        tablename =i.getStringExtra("tablename");
        categoryname =i.getStringExtra("category");
        subcategoryname =i.getStringExtra("subcategory");

        if(subcategoryname.equals("none"))
        {
            databaseReference =firebaseDatabase.getReference(tablename).child(categoryname);
        }
        else
        {
            databaseReference =firebaseDatabase.getReference(tablename).child(categoryname).child(subcategoryname);
        }
        btaddToFirebase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String dishname="";
        String dishDesc="";
        String dishprice="";
        if(etdishname.getText().toString().equals(""))
        {
          etdishname.setError("Enter dishname to proceed");
          etdishname.requestFocus();
        }
        else if(etdishdesc.getText().toString().equals(""))
        {
            etdishdesc.setError("Enter dish description to proceed");
            etdishdesc.requestFocus();
        }
        else if(etdishprice.getText().toString().equals(""))
        {
            etdishprice.setError("Enter dish price to proceed");
            etdishprice.requestFocus();
        }
        else
         {
            dishname = etdishname.getText().toString();
            dishDesc = etdishdesc.getText().toString();
            dishprice = etdishprice.getText().toString();

            dishprice = "$" + dishprice;
            DishClass dish = new DishClass();

            dish.setDescription(dishDesc);
            dish.setPrice(dishprice);

            databaseReference.child(dishname).setValue(dish);
            Toast.makeText(AddNewDishActivity.this, "Menu has been updated", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(AddNewDishActivity.this, GenericListViewDisplay.class);
            i.putExtra("tablename", tablename);
            i.putExtra("categoryname", categoryname);
            i.putExtra("subcategoryname", subcategoryname);
            startActivity(i);
            finish();
        }
    }
}
