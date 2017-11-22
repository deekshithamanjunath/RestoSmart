package com.example.giridhar.restosmart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class RemoveDishActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String categoryname,subcategoryname,tablename;
    ListView lvItems;
    ArrayList<Order> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_dish);
        lvItems =(ListView)findViewById(R.id.listOfItems);
        Intent i =getIntent();
        tablename = i.getStringExtra("tablename");
        categoryname =i.getStringExtra("category");
        subcategoryname =i.getStringExtra("subcategory");
        System.out.println(tablename);
        System.out.println(categoryname);
        System.out.println(subcategoryname);
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getData(Iterable<DataSnapshot> children)
    {
        dataList.clear();
        for(DataSnapshot data :children)
        {
            Order order = new Order();
            System.out.println(data.getKey());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Order finalOrderObj = orderObj;
        builder.setMessage("Are you sure you want to delete this item?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
             databaseReference.child(finalOrderObj.getDishName()).removeValue();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
            }
        });
        AlertDialog alertObj = builder.create();
        alertObj.show();

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
            Intent i = new Intent(RemoveDishActivity.this,ManagerStartupScreen.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
