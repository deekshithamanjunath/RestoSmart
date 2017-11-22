package com.example.giridhar.restosmart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckoutWithOrder extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
ArrayList<Order> orderedList = new ArrayList<>();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ListView orderedMenu;
    TextView tvTaxamount,tvTax,tvTotal,tvTotalAmount,tvcheckoutwithnoorder;
    Button btaddmore, btgenerateBill;
    FirebaseDatabase firebase;
    DatabaseReference databaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       orderedList = databaseHelper.getOrderedItemList(TableViewActivity.idOfOrder);
       if(orderedList.isEmpty())
       {
           setContentView(R.layout.checkout_form_itemsnotselected);
           tvcheckoutwithnoorder =(TextView)findViewById(R.id.textView19);
           tvcheckoutwithnoorder.setOnClickListener(this);
       }
       else
       {
           setContentView(R.layout.activity_checkout_with_order);

           orderedMenu = (ListView)findViewById(R.id.orderedMenuList);
           tvTax =(TextView)findViewById(R.id.textView8);
           tvTaxamount=(TextView)findViewById(R.id.textView17);
           tvTotal =(TextView)findViewById(R.id.textView16);
           tvTotalAmount=(TextView)findViewById(R.id.textView18);
           btaddmore =(Button)findViewById(R.id.addMoreItems);
           btgenerateBill =(Button)findViewById(R.id.checkout);

           btaddmore.setOnClickListener(this);
           btgenerateBill.setOnClickListener(this);

           tvTax.setText("Tax");
           tvTotal.setText("Total");

           firebase=FirebaseDatabase.getInstance();
           databaseref = firebase.getReference("tables");

           orderedMenu.setAdapter(new CustomAdapterForCheckoutForm(this,orderedList));
           orderedMenu.setOnItemClickListener(this);
           getTotalAmount();
       }

    }

    private void getTotalAmount()
    {
        ArrayList<Float>priceList = new ArrayList<>();
        float total =0;
       for(Order order : orderedList)
       {
           priceList.add(Float.valueOf(order.getDishPrice().substring(1).trim()));
       }
       for(Float i :priceList)
       {
           total = total + i;
       }
        float taxamt = (float) (0.0775 * total);
        taxamt = Math.round(taxamt);
        String finaltaxamt ="$"+taxamt;
        tvTaxamount.setText(finaltaxamt);
        total = Math.round(total +taxamt);
        String finaltotalamt ="$"+total;
       tvTotalAmount.setText(finaltotalamt);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addMoreItems:
                Intent navigateTo = new Intent(CheckoutWithOrder.this,RestaurantMenuActivity.class);
                finish();
                startActivity(navigateTo);
                break;
            case R.id.textView19:
                Intent navigate = new Intent(CheckoutWithOrder.this,RestaurantMenuActivity.class);
                finish();
                startActivity(navigate);
                break;
            case R.id.checkout:
                 placeOrder();
        }

    }

    private void placeOrder()
    {
        String orderid =TableViewActivity.idOfOrder;
        String res[] = orderid.split("(?<=\\D)(?=\\d)");

        final String tablename ="table"+res[1];
        databaseref.child(tablename).child("isBooked").setValue(false);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Customer is being served");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(6000);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run()
            {
                progressDialog.cancel();
                Toast.makeText(CheckoutWithOrder.this,"You will be presented with the check. Thank you",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CheckoutWithOrder.this,InvoiceBillActivity.class);
                i.putExtra("tablename",tablename);
                i.putExtra("totalamount",tvTotalAmount.getText().toString());
                finish();
                startActivity(i);
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 6000);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Order orderObj = new Order();
        orderObj = (Order) parent.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatabaseHelper databaseHelper=new DatabaseHelper(this);

        final Order finalOrderObj1 = orderObj;
        builder.setMessage("Are you sure you want to delete this item?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                databaseHelper.deleteItemFromList(TableViewActivity.idOfOrder, String.valueOf(finalOrderObj1.getQuantity()), finalOrderObj1.getDishName());
                Intent intent = getIntent();
                startActivity(intent);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(CheckoutWithOrder.this,RestaurantMenuActivity.class);
        startActivity(back);
        finish();
    }
}
