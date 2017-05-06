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

public class CustomRowAdapterForMenuList extends BaseAdapter
{
    Context mcontext;
    ArrayList<Order> dataArray;
    private static LayoutInflater inflater = null;

    public CustomRowAdapterForMenuList(Context context, ArrayList<Order> arr) {
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
            v= inflater.inflate(R.layout.custom_row_order_menu_listview,null);
        }
        TextView etdish =(TextView) v.findViewById(R.id.editdishname);
        TextView etdesc =(TextView) v.findViewById(R.id.editdishdescription);
        TextView etprice =(TextView) v.findViewById(R.id.edititemcost);
        etdish.setText(dataArray.get(position).getDishName());
        etdish.setTypeface(null, Typeface.BOLD);
        etdesc.setText(dataArray.get(position).getDishDescription());
        etprice.setText(dataArray.get(position).getDishPrice());
        return  v;
    }
}
