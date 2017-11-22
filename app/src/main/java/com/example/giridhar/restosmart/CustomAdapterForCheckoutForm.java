package com.example.giridhar.restosmart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by giridhar on 5/1/17.
 */

public class CustomAdapterForCheckoutForm extends BaseAdapter {
    Context mcontext;
    ArrayList<Order> dataArray;
    private static LayoutInflater inflater = null;
    public CustomAdapterForCheckoutForm(Context context, ArrayList<Order> arr) {
        mcontext=context;
        dataArray =arr;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
       return dataArray.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =convertView;
        if(v==null)
        {
            v= inflater.inflate(R.layout.custom_row_checkoutform,null);
        }
        TextView tvqty =(TextView)v.findViewById(R.id.textView9);
        TextView etdish =(TextView) v.findViewById(R.id.textView6);
        TextView etprice =(TextView) v.findViewById(R.id.textView7);
        etdish.setText(dataArray.get(position).getDishName());
        etdish.setTypeface(null, Typeface.BOLD);
        etprice.setText(dataArray.get(position).getDishPrice());
        tvqty.setText(String.valueOf(dataArray.get(position).getQuantity()));
        return  v;
    }
}
