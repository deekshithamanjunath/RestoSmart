package com.example.giridhar.restosmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RestaurantMenuActivity extends AppCompatActivity {
ListView lvMenu;
    ArrayList<String>tempList =new ArrayList<>();
    ArrayAdapter<String>tempadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        lvMenu=(ListView)findViewById(R.id.menuList);
    }
}
