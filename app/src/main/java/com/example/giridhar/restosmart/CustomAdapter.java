package com.example.giridhar.restosmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by giridhar on 4/22/17.
 */

public class CustomAdapter extends BaseAdapter
{
    private Context context;
    private final ArrayList<Integer> idsOfImages;

    public CustomAdapter(Context context, ArrayList<Integer> Imageid)
    {
        this.context = context;
        idsOfImages = Imageid;
    }
    @Override
    public int getCount() {
        return idsOfImages.size();
    }

    @Override
    public Object getItem(int position) {
        return idsOfImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(idsOfImages.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(200,240));
        return imageView;
    }
}
