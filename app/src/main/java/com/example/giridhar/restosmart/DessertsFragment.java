package com.example.giridhar.restosmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class DessertsFragment extends Fragment implements AdapterView.OnItemClickListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View v;
    ListView dessertsList;
    ArrayList<Order> dataList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v= inflater.inflate(R.layout.desserts_fragment,container,false);
        dessertsList =(ListView)v.findViewById(R.id.dessertsList);
        dessertsList.setOnItemClickListener(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("menu").child("Desserts");
        getDessertsList(databaseReference);

        return v;

    }

    private void getDessertsList(DatabaseReference databaseReference)
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
        for(DataSnapshot data: children)
        {
            Order resObj = new Order();
            resObj.setDishName(data.getKey());
            resObj.setDishDescription(data.child("description").getValue().toString());
            resObj.setDishPrice((data.child("price").getValue().toString()));
            resObj.setQuantity(1);
            dataList.add(resObj);
        }
        System.out.println("The array list is "+dataList);
        dessertsList.setAdapter(new CustomRowAdapterForMenuList(getContext(),dataList));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
