package com.example.giridhar.restosmart;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerStartupScreen extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
RadioGroup radioGroup;
RadioButton rdappetizer,rdbeverages,rdentree,rdsoupsalad,rddesserts;
TextView tvcategory,tvsubcategory;
FirebaseDatabase firebaseDatabase;
Spinner spsubcategory;
DatabaseReference databaseReference;
ArrayList<String> subcategorylist =  new ArrayList<>();
Button btproceed;
String selectedCategory,selectedSubCategory;
FirebaseAuth firebaseAuth;


ArrayAdapter<String> adapterForSubCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_startup_screen);

        tvcategory =(TextView)findViewById(R.id.textView21);
        radioGroup =(RadioGroup)findViewById(R.id.radioGroup);
        rdbeverages =(RadioButton)findViewById(R.id.beveragescategory);
        rdappetizer =(RadioButton)findViewById(R.id.appetizercategory);
        rdsoupsalad =(RadioButton)findViewById(R.id.soupssaladcategory);
        rdentree =(RadioButton)findViewById(R.id.entreecategory);
        rddesserts =(RadioButton)findViewById(R.id.dessertscategory);
        tvsubcategory=(TextView)findViewById(R.id.textView24);
        spsubcategory =(Spinner)findViewById(R.id.spinner);
        btproceed =(Button)findViewById(R.id.button3);

        tvsubcategory.setVisibility(View.GONE);
        spsubcategory.setVisibility(View.GONE);

        adapterForSubCategory = new ArrayAdapter<String>(ManagerStartupScreen.this,android.R.layout.simple_spinner_item,subcategorylist);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("menu");
    }

    @Override
    protected void onStart() {
        super.onStart();
        spsubcategory.setOnItemSelectedListener(this);
        spsubcategory.setAdapter(adapterForSubCategory);
        radioGroup.setOnCheckedChangeListener(this);
        btproceed.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
     switch (checkedId)
     {
         case R.id.appetizercategory:
                          selectedCategory ="Appetizers";
                          selectedSubCategory="none";
                          getData("Appetizers");
                          System.out.println(checkedId);
                          break;
         case R.id.beveragescategory:
                          selectedCategory ="Beverages";
                          selectedSubCategory="none";
                          getData("Beverages");
                          System.out.println(checkedId);
                          break;
         case R.id.soupssaladcategory:
                          selectedCategory ="Soups & Salads";
                          selectedSubCategory="none";
                          getData("Soups & Salads");
                          System.out.println(checkedId);
                          break;
         case R.id.entreecategory:
                           selectedCategory ="Entree";
                           selectedSubCategory="none";
                           getData("Entree");
                           System.out.println(checkedId);
                           break;
         case R.id.dessertscategory:
                           selectedCategory ="Desserts";
                           selectedSubCategory="none";
                           getData("Desserts");
                           System.out.println(checkedId);
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

      for(DataSnapshot data :children)
      {

          for(DataSnapshot childrendata: data.getChildren())
          {

              if(childrendata.hasChildren())
              {
                  spsubcategory.setSelection(0);
                  tvsubcategory.setVisibility(View.VISIBLE);
                  spsubcategory.setVisibility(View.VISIBLE);
                  if(!subcategorylist.contains(data.getKey())) subcategorylist.add(data.getKey());
              }
              else {
                  tvsubcategory.setVisibility(View.GONE);
                  spsubcategory.setVisibility(View.GONE);
              }
          }
      }
      adapterForSubCategory.notifyDataSetChanged();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        selectedSubCategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //
    }

    @Override
    public void onClick(View v)
    {
      if(radioGroup.getCheckedRadioButtonId()==-1)
      {
          Toast.makeText(ManagerStartupScreen.this,"Please choose a category to proceed",Toast.LENGTH_SHORT).show();
      }
      else
      {
          Intent navigate = new Intent(ManagerStartupScreen.this, ManagerOptionsActivity.class);
          navigate.putExtra("tablename", "menu");
          navigate.putExtra("category", selectedCategory);
          navigate.putExtra("subcategory", selectedSubCategory);
          startActivity(navigate);
      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_manager_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id= item.getItemId();
        if(id==R.id.signout)
        {
            firebaseAuth.signOut();
            Intent i = new Intent(ManagerStartupScreen.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

    }
}
