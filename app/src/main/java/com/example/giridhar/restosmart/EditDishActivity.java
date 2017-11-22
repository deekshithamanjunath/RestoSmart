package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditDishActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
ListView lvItems;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
String categoryname,subcategoryname,tablename;
ArrayList<Order> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        lvItems =(ListView)findViewById(R.id.itemsList);

        Intent i =getIntent();
        tablename = i.getStringExtra("tablename");
        categoryname =i.getStringExtra("category");
        subcategoryname =i.getStringExtra("subcategory");

        firebaseDatabase=FirebaseDatabase.getInstance();
        if(subcategoryname.equals("none"))
        {
            databaseReference = firebaseDatabase.getReference(tablename).child(categoryname);
        }
        else
        {
            databaseReference = firebaseDatabase.getReference(tablename).child(categoryname).child(subcategoryname);
        }
        populateList(databaseReference);
        lvItems.setOnItemClickListener(this);
    }

    private void populateList(DatabaseReference databaseReference)
    {
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            getData(dataSnapshot.getChildren());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            //
        }
    });
    }

    private void getData(Iterable<DataSnapshot> children)
    {
        dataList.clear();
        for(DataSnapshot data :children)
        {
          Order order = new Order();

          order.setDishName(data.getKey());
          order.setDishDescription(data.child("description").getValue().toString());
          order.setDishPrice(data.child("price").getValue().toString());
          dataList.add(order);
        }
        lvItems.setAdapter(new CustomRowAdapterForMenuList(this,dataList));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
      Order orderObj = new Order();
      orderObj = (Order) parent.getItemAtPosition(position);
      Intent i = new Intent(EditDishActivity.this,ModifyItemActivity.class);

      i.putExtra("tablename",tablename);
      i.putExtra("category",categoryname);
      i.putExtra("subcategory",subcategoryname);
      i.putExtra("dishname",orderObj.getDishName());
      startActivity(i);
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
            Intent i = new Intent(EditDishActivity.this,ManagerStartupScreen.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
