package com.example.giridhar.restosmart;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerStartupScreen extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
RadioGroup radioGroup;
RadioButton rdappetizer,rdbeverages,rdentree,rdsoupsalad,rddesserts;
TextView tvcategory,tvsubcategory,tvitem;
FirebaseDatabase firebaseDatabase;
Spinner spsubcategory,spitem;
DatabaseReference databaseReference;
ArrayList<String> itemsList = new ArrayList<>();
ArrayList<String> subcategorylist =  new ArrayList<>();
ArrayAdapter<String> adapterForItems;
ArrayAdapter<String> adapterForSubCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_startup_screen);
        tvcategory =(TextView)findViewById(R.id.textView21);

        tvitem=(TextView)findViewById(R.id.textView25);
        radioGroup =(RadioGroup)findViewById(R.id.radioGroup);
        rdbeverages =(RadioButton)findViewById(R.id.beveragescategory);
        rdappetizer =(RadioButton)findViewById(R.id.appetizercategory);
        rdsoupsalad =(RadioButton)findViewById(R.id.soupssaladcategory);
        rdentree =(RadioButton)findViewById(R.id.entreecategory);
        rddesserts =(RadioButton)findViewById(R.id.dessertscategory);
        tvsubcategory=(TextView)findViewById(R.id.textView24);
        spsubcategory =(Spinner)findViewById(R.id.spinner);
        tvsubcategory.setVisibility(View.GONE);
        spsubcategory.setVisibility(View.GONE);
        spitem =(Spinner)findViewById(R.id.spinner2);
        adapterForItems = new ArrayAdapter<String>(ManagerStartupScreen.this,android.R.layout.simple_spinner_item,itemsList);
        adapterForSubCategory = new ArrayAdapter<String>(ManagerStartupScreen.this,android.R.layout.simple_spinner_item,subcategorylist);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("menu");
    }

    @Override
    protected void onStart() {
        super.onStart();
        spitem.setOnItemSelectedListener(this);
        spitem.setAdapter(adapterForItems);
        spsubcategory.setOnItemSelectedListener(this);
        spsubcategory.setAdapter(adapterForSubCategory);
        radioGroup.setOnCheckedChangeListener(this);

    }



    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
     switch (checkedId)
     {
         case R.id.appetizercategory:
                          getData("Appetizers");
                          System.out.println("Appetizer selected");
                          break;
         case R.id.beveragescategory:
                          getData("Beverages");
                          System.out.println("Beverage selected");
                          break;
         case R.id.soupssaladcategory:
                          System.out.println("Soups and salads selected");
                          break;
         case R.id.entreecategory:
                           System.out.println("Entree selected");
                           break;
         case R.id.dessertscategory:
                           System.out.println("Dessert selected");
                           break;
     }
    }

    private void getData(String s)
    {
        databaseReference.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                   getChildrenOfNode(dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getChildrenOfNode(Iterable<DataSnapshot> children)
    {
        subcategorylist.clear();
        itemsList.clear();
      for(DataSnapshot data :children)
      {
          for(DataSnapshot childrendata: data.getChildren())
          {
              if(childrendata.hasChildren())
              {
                  System.out.println(childrendata.getKey());
                  tvsubcategory.setVisibility(View.VISIBLE);
                  spsubcategory.setVisibility(View.VISIBLE);
                  subcategorylist.add(data.getKey());


              }
              else
              {
                  tvsubcategory.setVisibility(View.GONE);
                  spsubcategory.setVisibility(View.GONE);
                  itemsList.add(data.getKey());
              }
          }

      }
        adapterForItems.notifyDataSetChanged();
    }

    private void getSubChildren(Iterable<DataSnapshot> children)
    {
        itemsList.clear();
      for(DataSnapshot data :children)
      {
          itemsList.add(data.getKey());
      }
      adapterForItems.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
      String selectedsub = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
