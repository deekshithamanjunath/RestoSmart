package com.example.giridhar.restosmart;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.giridhar.restosmart.TableViewActivity.idOfOrder;

public class RestaurantMenuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    FirebaseAuth firebaseAuth;
    DatabaseHelper databaseHelper;
    DatabaseReference dataBaseRef;
    FirebaseDatabase firebaseDataBase;

    public String fragmentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        getSupportActionBar().setTitle("RestoSmart");
        firebaseAuth=FirebaseAuth.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        databaseHelper=new DatabaseHelper(this);
        firebaseDataBase= FirebaseDatabase.getInstance();
        dataBaseRef =firebaseDataBase.getReference("tables");
        if(id==R.id.cart)
        {
            Intent i = new Intent(RestaurantMenuActivity.this,CheckoutWithOrder.class);
            finish();
            startActivity(i);
        }
        else if(id==R.id.logout)
        {
            Intent i = new Intent(RestaurantMenuActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            String res[] = idOfOrder.split("(?<=\\D)(?=\\d)");
            String tablename = "table" + res[1];
            dataBaseRef.child(tablename).child("isBooked").setValue(false);
            databaseHelper.deleteFromDatabase(idOfOrder);
            firebaseAuth.signOut();

        }
        else
        {
            //
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position)
    {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    BeveragesFragment beveragesFragment = new BeveragesFragment();
                    Bundle bdl = new Bundle();
                    bdl.putInt("fragname",0);
                    beveragesFragment.setArguments(bdl);
                    return beveragesFragment;
                case 1:
                    AppetizersFragment appetizersFragment = new AppetizersFragment();
                    Bundle bdlobj = new Bundle();
                    bdlobj.putInt("fragname",1);
                    appetizersFragment.setArguments(bdlobj);
                    return appetizersFragment;
                case 2:
                    SoupsSaladsFragment soupsSaladsFragment = new SoupsSaladsFragment();
                    Bundle bdlobj1 = new Bundle();
                    bdlobj1.putInt("fragname",2);
                    soupsSaladsFragment.setArguments(bdlobj1);
                    return soupsSaladsFragment;
                case 3:
                    EntreeFragment entreeFragment = new EntreeFragment();
                    Bundle bdlobj2 = new Bundle();
                    bdlobj2.putInt("fragname",3);
                    entreeFragment.setArguments(bdlobj2);
                    return entreeFragment;
                case 4:
                    DessertsFragment dessertsFragment = new DessertsFragment();
                    Bundle bdlobj3 = new Bundle();
                    bdlobj3.putInt("frqagname",4);
                    dessertsFragment.setArguments(bdlobj3);
                    return dessertsFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Beverages";
                case 1:
                    return "Appetizers";
                case 2:
                    return "Soups & Salads";
                case 3:
                    return "Entree";
                case 4:
                    return "Dessert";
            }
            return null;
        }
    }
    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    @Override
    public void onBackPressed() {

    }
}
