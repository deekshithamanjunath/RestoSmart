package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ManageOrderActivity extends AppCompatActivity implements View.OnClickListener {
String dishname,dishdescription, dishprice;
    TextView tvdishname,tvdishdescription,tvprice,etQty;
    ImageButton ibreduceQty,ibincreaseQty;
    Button btaddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);
        tvdishname = (TextView) findViewById(R.id.editText);
        tvdishdescription = (TextView) findViewById(R.id.textView11);
        tvprice = (TextView) findViewById(R.id.textView10);
        etQty = (TextView) findViewById(R.id.editText3);
        ibincreaseQty = (ImageButton) findViewById(R.id.increaseQty);
        ibreduceQty = (ImageButton) findViewById(R.id.reduceQty);
        btaddToCart =(Button)findViewById(R.id.addToCartManageOrder);
      //  Intent getData = getIntent();
       // dishname = getData.getStringExtra("dishname");
       // dishdescription = getData.getStringExtra("dishdescription");
        //dishprice = getData.getStringExtra("dishprice");
          dishprice ="3";
       // tvdishname.setText(dishname);
       // tvdishdescription.setText(dishdescription);
        tvprice.setText(dishprice);
        etQty.setText(String.valueOf(1));

    }

    @Override
    protected void onStart() {
        super.onStart();
        ibincreaseQty.setOnClickListener(this);
        ibreduceQty.setOnClickListener(this);
        btaddToCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        switch(v.getId())
     {
         case R.id.increaseQty:
                       addMoreItem();
                       break;
         case R.id.reduceQty:
                       removeItem();
                       break;
         case R.id.addToCartManageOrder:
                       pushToCart();
                       break;
     }

    }




    private void addMoreItem()
    {
        String qty = etQty.getText().toString();
        int quantity = Integer.parseInt(qty);
        quantity = quantity+1;
        etQty.setText(String.valueOf(quantity));
        int originaldishprice= Integer.parseInt(dishprice);
        int price = quantity * originaldishprice;
        String newprice = "$"+price;
        tvprice.setText(newprice);

    }

    private void removeItem()
    {
        String qty   = etQty.getText().toString();
        int quantity = Integer.parseInt(qty);
        if(quantity<=0)
        {
            Toast.makeText(ManageOrderActivity.this,"No more items to be removed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            quantity = quantity-1;
            int originaldishprice= Integer.parseInt(dishprice);
            //int price = Integer.parseInt(tvprice.getText().toString());
            int price = quantity *originaldishprice;
            String newprice = "$"+price;
            tvprice.setText(newprice);
            etQty.setText(String.valueOf(quantity));
        }

    }

    private void pushToCart()
    {
//        Intent navigateToCheckout= new Intent(ManageOrderActivity.this,CheckoutWithOrder.class);
//        navigateToCheckout.putExtra("dishname", dishname);
//        navigateToCheckout.putExtra("dishdesc", dishdescription);
//        navigateToCheckout.putExtra("dishprice",dishprice);
//        startActivity(n);

    }
}
