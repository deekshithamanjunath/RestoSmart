package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GenericListViewDisplay extends AppCompatActivity {
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
ListView lvitems;
ArrayList<Order>dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic_list_view_display);

        lvitems =(ListView)findViewById(R.id.genericList);
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent i =getIntent();
        String tablename =i.getStringExtra("tablename");
        String categoryname =i.getStringExtra("categoryname");
        String subcategoryname =i.getStringExtra("subcategoryname");

        if(subcategoryname.equals("none"))
        {
            databaseReference =firebaseDatabase.getReference(tablename).child(categoryname);
            populateListWithNoSubcategory(databaseReference);
        }
        else
        {
            databaseReference =firebaseDatabase.getReference(tablename).child(categoryname);
            populateCategoryWithSubCategory(databaseReference);
        }
    }

    private void populateListWithNoSubcategory(DatabaseReference databaseReference)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchDataNoSubCat(dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void fetchDataNoSubCat(Iterable<DataSnapshot> children)
    {
        dataList.clear();
        for(DataSnapshot data: children)
        {
            Order resObj = new Order();

            resObj.setDishName(data.getKey());
            resObj.setDishDescription(data.child("description").getValue().toString());
            resObj.setDishPrice((data.child("price").getValue().toString()));
            resObj.setQuantity(1);
            dataList.add(resObj);
        }
        lvitems.setAdapter(new CustomRowAdapterForMenuList(GenericListViewDisplay.this,dataList));


    }

    private void populateCategoryWithSubCategory(final DatabaseReference databaseReference)
    {
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot)
             {
                 fetchDatawithSubCat(dataSnapshot.getChildren());
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

    }

    private void fetchDatawithSubCat(Iterable<DataSnapshot> children)
    {
        dataList.clear();
        ArrayList<String>keyList = new ArrayList<>();
        for(DataSnapshot data: children)
        {
            Iterable<DataSnapshot> innerChild = data.getChildren();
            for(DataSnapshot test : innerChild)
            {
                keyList.add(test.getKey());
            }
            for(String s :keyList)
            {
                if(data.child(s).hasChildren())
                {
                    Order resObj = new Order();

                    resObj.setDishName(data.child(s).getKey());
                    resObj.setDishDescription(data.child(s).child("description").getValue().toString());
                    resObj.setDishPrice(data.child(s).child("price").getValue().toString());
                    resObj.setItemCategory(data.getKey());
                    resObj.setQuantity(1);
                    dataList.add(resObj);
                }
            }
            lvitems.setAdapter(new CustomAdapterForBeveragesFragment(GenericListViewDisplay.this,dataList));
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
            Intent i = new Intent(GenericListViewDisplay.this,ManagerStartupScreen.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
