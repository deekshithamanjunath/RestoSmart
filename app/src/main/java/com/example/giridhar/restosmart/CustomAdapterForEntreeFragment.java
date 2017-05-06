package com.example.giridhar.restosmart;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giridhar on 4/29/17.
 */

public class CustomAdapterForEntreeFragment extends ArrayAdapter<EntreeInterface> {

    private Context context;
    private ArrayList<EntreeInterface> entrees;
    private LayoutInflater layoutInflater;

    public CustomAdapterForEntreeFragment(Context context, ArrayList<EntreeInterface> entrees) {
        super(context, 0, entrees);
        this.context = context;
        this.entrees = entrees;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return entrees.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View v = view;
        final EntreeInterface entreeInterface = entrees.get(position);

        if(entreeInterface!=null) {
            if (entreeInterface.isCategory()) {
                EntreeCategory entreeCategory = (EntreeCategory) entreeInterface;
                v = layoutInflater.inflate(R.layout.list_entree_category_section, null);

                v.setOnClickListener(null);

                final TextView categoryText = (TextView) v.findViewById(R.id.list_entree_category_text);
                categoryText.setText(entreeCategory.getHeading());
            }

            else if (entreeInterface.isDish()) {
                    EntreeDish entreeDish = (EntreeDish) entreeInterface;
                    v = layoutInflater.inflate(R.layout.list_entree_dish_section, null);

                    final TextView heading = (TextView) v.findViewById(R.id.list_entree_dish_heading);
                    final TextView subHeading = (TextView) v.findViewById(R.id.list_entree_dish_subheading);

                    if (heading != null)
                        heading.setText(entreeDish.heading);

                    if (subHeading != null)
                        subHeading.setText(entreeDish.subHeading);
            }
        }

        return v;
    }

}
