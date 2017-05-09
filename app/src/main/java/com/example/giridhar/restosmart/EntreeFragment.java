package com.example.giridhar.restosmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by giridhar on 4/28/17.
 */

public class EntreeFragment extends Fragment implements AdapterView.OnItemClickListener
{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Order> dataList = new ArrayList<>();
    ListView entreeList;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v= inflater.inflate(R.layout.entree_fragment,container,false);
        entreeList = (ListView) v.findViewById(R.id.entreeList);
        entreeList.setOnItemClickListener(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("menu").child("Entree");
        getEntreeList(databaseReference);
        return v;

    }

    private void getEntreeList(DatabaseReference databaseReference)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getData(dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getData(Iterable<DataSnapshot> children)
    {
        dataList.clear();
        ArrayList<String>keyList = new ArrayList<>();
        ArrayList<String>childrenKeyList= new ArrayList<>();
        for(DataSnapshot data: children)
        {

            Iterable<DataSnapshot> innerChild = data.getChildren();
            for(DataSnapshot test : innerChild)
            {
                keyList.add(test.getKey());
            }
            for(String s :keyList)
            {
                if(data.child(s).hasChildren())
                {
                    Order resObj = new Order();
                    resObj.setDishName(data.child(s).getKey());
                    resObj.setDishDescription(data.child(s).child("description").getValue().toString());
                    resObj.setDishPrice(data.child(s).child("price").getValue().toString());
                    resObj.setItemCategory(data.getKey());
                    resObj.setQuantity(1);
                    dataList.add(resObj);
                }
            }
            entreeList.setAdapter(new CustomAdapterForBeveragesFragment(getContext(),dataList));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Order order = new Order();
        order = (Order) parent.getItemAtPosition(position);
        Intent i =  new Intent(getActivity(),ManageOrderActivity.class);
        i.putExtra("dishname",order.getDishName());
        i.putExtra("dishdescription",order.getDishDescription());
        i.putExtra("dishprice",order.getDishPrice());
        i.putExtra("dishquantity",order.getQuantity());
        startActivity(i);

    }
}
