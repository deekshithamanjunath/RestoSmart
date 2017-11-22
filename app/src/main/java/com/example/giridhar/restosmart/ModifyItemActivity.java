package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyItemActivity extends AppCompatActivity implements View.OnClickListener {
EditText etPrice;
Button btupdatePrice;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
String tablename,categoryname,subcategoryname,dishname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        etPrice =(EditText)findViewById(R.id.editText10);
        btupdatePrice=(Button)findViewById(R.id.updatePrice);

        firebaseDatabase =FirebaseDatabase.getInstance();

        Intent i =getIntent();
        tablename = i.getStringExtra("tablename");
        categoryname =i.getStringExtra("category");
        subcategoryname =i.getStringExtra("subcategory");
        dishname=i.getStringExtra("dishname");

        if(subcategoryname.equals("none"))
        {
            databaseReference = firebaseDatabase.getReference(tablename).child(categoryname);
        }
        else
        {
            databaseReference = firebaseDatabase.getReference(tablename).child(categoryname).child(subcategoryname);
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        btupdatePrice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
     if(etPrice.getText().toString().equals(""))
     {
        etPrice.setError("Enter new price to update");
        etPrice.requestFocus();
     }
     else {
         String price = etPrice.getText().toString();
         price = "$" + price;
         databaseReference.child(dishname).child("price").setValue(price);
         this.finish();
     }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_generic_listview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id= item.getItemId();
        if(id==R.id.closebtn)
        {
            Intent i = new Intent(ModifyItemActivity.this,ManagerStartupScreen.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
