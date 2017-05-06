package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
    int dishqty;
    TextView tvdishname,tvdishdescription,tvprice,etQty;
    ImageButton ibreduceQty,ibincreaseQty;
    Button btaddToCart;
    int tagname;
   // RestaurantMenuActivity res;
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
        Intent getData = getIntent();
        dishname = getData.getStringExtra("dishname");
        dishdescription = getData.getStringExtra("dishdescription");
        dishprice = getData.getStringExtra("dishprice");
        dishqty =   getData.getIntExtra("dishquantity",0);
        //  dishprice ="3";
        tagname = getData.getIntExtra("fragname",0);
        System.out.println("In manager order" + tagname);
        tvdishname.setText(dishname);
        tvdishdescription.setText(dishdescription);
        tvprice.setText(dishprice);
        etQty.setText(String.valueOf(dishqty));
        //res = new RestaurantMenuActivity();

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
        String tempprice =dishprice.substring(1);
        float originaldishprice= Float.parseFloat(tempprice);
        float price = Math.round(quantity * originaldishprice);
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
            String tempprice =dishprice.substring(1);

            tempprice = tempprice.trim();
            if(tempprice.equals(""))
            {
                 //
            }
            else
            {
                float originalprice = Float.parseFloat(tempprice);
                System.out.println(originalprice);
                float price = quantity * originalprice;
                String newprice = "$"+price;
                tvprice.setText(newprice);
                etQty.setText(String.valueOf(quantity));
            }
        }

    }

    private void pushToCart()
    {
        DatabaseHelper db = new DatabaseHelper(this);
        Order order =  new Order();
        order.setOrderId(TableViewActivity.idOfOrder);
        order.setQuantity(Integer.parseInt(etQty.getText().toString()));
        order.setDishPrice(tvprice.getText().toString());
        order.setDishName(tvdishname.getText().toString());
        order.setDishDescription(tvdishdescription.getText().toString());
        db.addItemToOrderList(order);
        Intent navigateToCheckout= new Intent(ManageOrderActivity.this,RestaurantMenuActivity.class);
        startActivity(navigateToCheckout);

    }
}
