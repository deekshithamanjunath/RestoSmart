package com.example.giridhar.restosmart;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.giridhar.restosmart.R.id.center;
import static com.example.giridhar.restosmart.R.id.textView42;

public class InvoiceBillActivity extends AppCompatActivity {
    ArrayList<Order> orderedList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    ListView lvitemslist;
    FirebaseAuth firebaseAuth;
    TextView tvservername,tvtablename,tvtotalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invoice_bill);

        tvservername=(TextView)findViewById(R.id.textView42);
        tvtablename=(TextView)findViewById(R.id.textView43);
        tvtotalamount=(TextView)findViewById(R.id.textView49);
        lvitemslist=(ListView)findViewById(R.id.listOffinalItems);

        firebaseAuth=FirebaseAuth.getInstance();

        Intent i =getIntent();
        String email =firebaseAuth.getCurrentUser().getEmail();
        String [] mail = email.split("@");
        String name = mail[0].substring(0,1).toUpperCase()+mail[0].substring(1);

        tvservername.setText(name);
        tvservername.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);

        String table = i.getStringExtra("tablename");
        String newtable = table.substring(0,1).toUpperCase()+table.substring(1);

        tvtablename.setText(newtable);
        tvtablename.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);

        String total =" Your Total is "+ i.getStringExtra("totalamount");
        tvtotalamount.setText(total);

        databaseHelper=new DatabaseHelper(this);
        orderedList= databaseHelper.getOrderedItemList(TableViewActivity.idOfOrder);
        fillLayout();
    }

    private void fillLayout()
    {
       lvitemslist.setAdapter(new CustomAdapterForCheckoutForm(InvoiceBillActivity.this,orderedList));
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_invoice,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id= item.getItemId();
        if(id==R.id.paid)
        {
            Toast.makeText(this, "Bill Paid", Toast.LENGTH_SHORT).show();
            databaseHelper.deleteFromDatabase(TableViewActivity.idOfOrder);
            Intent i = new Intent(InvoiceBillActivity.this,TableViewActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
