package com.example.giridhar.restosmart;

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
    ArrayList<EntreeInterface> dataList = new ArrayList<>();
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
        ArrayList<EntreeCategory>categorylist= new ArrayList<>();
        ArrayList<EntreeDish> dishList = new ArrayList<>();

        for(DataSnapshot data: children)
        {

            //String s = data.getKey();
            String name = data.getKey();
           // System.out.println("The entree is "+name);
            //dataList.add(new EntreeCategory(name));
            categorylist.clear();
            categorylist.add(new EntreeCategory(name));

            dataList.add(new EntreeCategory(name));

            for(EntreeCategory entreeCategory :categorylist)
            {
                System.out.println("Inside category " + entreeCategory.getHeading());
            }
            Iterable<DataSnapshot> innerChild = data.getChildren();
            for(DataSnapshot test : innerChild)
            {
                keyList.add(test.getKey());
               // System.out.println(test.getKey());
            }

            for(String s :keyList)
            {
                if(data.child(s).hasChildren())
                {
                    dishList.clear();
                    Order resObj = new Order();
                    resObj.setDishName(data.child(s).getKey());
                    resObj.setDishDescription(data.child(s).child("description").getValue().toString());
                    resObj.setDishPrice(data.child(s).child("price").getValue().toString());
                    resObj.setItemCategory(data.getKey());
                    resObj.setQuantity(1);
                    String subName = resObj.getDishName();
                   // System.out.println("Dish selected is "+subName);
                    dataList.add(new EntreeDish(name,subName));
                }
            }

            for(EntreeDish entreeDish:dishList)
            {
                System.out.println("Dishes are "+ entreeDish.heading + " ? " + entreeDish.subHeading);
            }

            //System.out.println("The array list is "+dataList);
           // CustomAdapterForEntreeFragment adapterForEntreeFragment = new CustomAdapterForEntreeFragment(this.getContext(),dataList);
            //entreeList.setAdapter(new ArrayAdapter<EntreeDish>(getActivity(),android.R.layout.simple_list_item_1,dishList));
            entreeList.setAdapter(new CustomAdapterForEntreeFragment(getContext(),dataList));
        }
      //  entreeList.setAdapter(new ArrayAdapter<EntreeDish>(getActivity(),android.R.layout.simple_list_item_1,dishList));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
