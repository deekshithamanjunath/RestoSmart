package com.example.giridhar.restosmart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckoutWithOrder extends AppCompatActivity implements View.OnClickListener{
ArrayList<Order> orderedList = new ArrayList<>();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ListView orderedMenu;
    TextView tvTaxamount,tvTax,tvTotal,tvTotalAmount,tvcheckoutwithnoorder;
    Button btaddmore, btgenerateBill;

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
           orderedMenu.setAdapter(new CustomAdapterForCheckoutForm(this,orderedList));
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
                startActivity(navigateTo);
                break;
            case R.id.textView19:
                Intent navigate = new Intent(CheckoutWithOrder.this,RestaurantMenuActivity.class);
                startActivity(navigate);
                break;
        }

    }
}
