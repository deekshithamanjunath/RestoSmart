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
 * Created by giridhar on 4/28/17.
 */

public class CustomAdapterForBeveragesFragment extends BaseAdapter {
    Context mcontext;
    ArrayList<Order> dataArray;
    private static LayoutInflater inflater = null;
    public CustomAdapterForBeveragesFragment(Context context, ArrayList<Order> arr) {
        mcontext=context;
        dataArray =arr;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
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
            v= inflater.inflate(R.layout.custom_row_beveragesfragment,null);
        }
        TextView tvitemCategory =(TextView)v.findViewById(R.id.textView13);
        TextView etdish =(TextView) v.findViewById(R.id.textView12);
        TextView etdesc =(TextView) v.findViewById(R.id.textView15);
        TextView etprice =(TextView) v.findViewById(R.id.textView14);
        etdish.setText(dataArray.get(position).getDishName());
        etdish.setTypeface(null, Typeface.BOLD);
        etdesc.setText(dataArray.get(position).getDishDescription());
        etprice.setText(dataArray.get(position).getDishPrice());
        String category ="("+dataArray.get(position).getItemCategory()+")";
        tvitemCategory.setText(category);
        tvitemCategory.setTypeface(null,Typeface.BOLD);
        return  v;
    }
}
