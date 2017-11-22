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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TableViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
FirebaseDatabase firebaseDataBase;
    DatabaseReference dataBaseRef;
    DatabaseHelper databaseHelper;

    GridView gridView;
    ArrayList<Boolean>tables = new ArrayList<>();
    ArrayList<Integer>imageIds=new ArrayList<>();
    String customerName;
    public static String idOfOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        firebaseDataBase = FirebaseDatabase.getInstance();
        dataBaseRef = firebaseDataBase.getReference("tables");
        gridView = (GridView) findViewById(R.id.grids);
        getCurrentTableStatus();
        gridView.setOnItemClickListener(this);
    }


    private void getCurrentTableStatus()
    {
     dataBaseRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot)
         {
            getTableData(dataSnapshot.getChildren());
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
    }

    private void getTableData(Iterable<DataSnapshot> children)
    {
        tables.clear();
        for(DataSnapshot data:children)
        {
            tables.add((Boolean) data.child("isBooked").getValue());
        }
        populateTableAvailability();
      return;
    }


    private void populateTableAvailability() {
        if (imageIds.isEmpty()) {
            for (Boolean b : tables) {
                if (b == true) {
                    imageIds.add(R.drawable.tablebooked);
                } else {
                    imageIds.add(R.drawable.tableavailable);
                }
            }
           gridView.setAdapter(new CustomAdapter(TableViewActivity.this,imageIds));
        }
        else
        {
            imageIds.clear();
            for (Boolean b : tables) {
                if (b == true) {
                    imageIds.add(R.drawable.tablebooked);
                } else {

                    imageIds.add(R.drawable.tableavailable);
                }
            }
           gridView.setAdapter(new CustomAdapter(TableViewActivity.this, imageIds));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
      final int tablenumber = position +1;
      final String tableName = "table"+tablenumber;
      boolean flagSet = tables.get(position);
        if(flagSet==true)
        {
            Toast.makeText(TableViewActivity.this,"Table already taken",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(TableViewActivity.this,"Table Available",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Customer Name");

            final EditText input = new EditText(this);
            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    if(input.getText().toString().equals(""))
                    {
                        Toast.makeText(TableViewActivity.this,"Please enter the customer name to proceed",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        customerName = input.getText().toString();
                        idOfOrder = customerName + tablenumber;
                        dataBaseRef.child(tableName).child("isBooked").setValue(true);
                        Intent i = new Intent(TableViewActivity.this, RestaurantMenuActivity.class);
                        startActivity(i);
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(input.getText().toString().isEmpty())
                    {
                        Toast.makeText(TableViewActivity.this,"Please enter the customer name to proceed",Toast.LENGTH_SHORT);
                    }

                }
            });

            alert.show();
          }

    }

    private void getCustomerName(EditText input)
    {
        customerName = input.getText().toString();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_manager_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        databaseHelper=new DatabaseHelper(this);
        int id= item.getItemId();
        if(id==R.id.signout)
        {
            if(idOfOrder==null)
            {
                Intent i = new Intent(TableViewActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(TableViewActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                String res[] = idOfOrder.split("(?<=\\D)(?=\\d)");
                String tablename = "table" + res[1];
                dataBaseRef.child(tablename).child("isBooked").setValue(false);
                databaseHelper.deleteFromDatabase(TableViewActivity.idOfOrder);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }
}
