package com.example.giridhar.restosmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by giridhar on 4/28/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="RESTAURANTORDERS";
    private static final String DATABASE_TABLE_NAME="ORDERS";

    private static final String COLUMN_OrderId="orderId";
    private static final String COLUMN_Quantity="quantity";
    private static final String COLUMN_DishName="dishName";
    private static final String COLUMN_DishDescription="dishDescription";
    private static final String COLUMN_DishPrice="dishPrice";

    public DatabaseHelper(Context context)
    {
      super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
        String query =  " CREATE TABLE " + DATABASE_TABLE_NAME + " ( " + COLUMN_OrderId + " TEXT, " + COLUMN_Quantity + " INTEGER, " + COLUMN_DishName + " TEXT, " + COLUMN_DishDescription + " TEXT, " + COLUMN_DishPrice + " NUMBER " + " ) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public void addItemToOrderList(Order bevOrder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_OrderId,bevOrder.getOrderId());
        contentValues.put(COLUMN_Quantity,bevOrder.getQuantity());
        contentValues.put(COLUMN_DishName,bevOrder.getDishName());
        contentValues.put(COLUMN_DishDescription,bevOrder.getDishDescription());
        contentValues.put(COLUMN_DishPrice,bevOrder.getDishPrice());
        db.insert(DATABASE_TABLE_NAME,null,contentValues);
        db.close();
    }
    public ArrayList<Order> getOrderedItemList(String customerid)
    {
        ArrayList<Order>getItemList =new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery( " SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE " + COLUMN_OrderId + " = '" + customerid + "'",null);
        while(cursor.moveToNext())
        {
           Order order = new Order();
           order.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_Quantity)));
           order.setDishName(cursor.getString(cursor.getColumnIndex(COLUMN_DishName)));
           order.setDishDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DishDescription)));
           order.setDishPrice(cursor.getString(cursor.getColumnIndex(COLUMN_DishPrice)));
           getItemList.add(order);
        }
      return  getItemList;
    }

    public void deleteFromDatabase(String orderid)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String query = " DELETE " + " FROM " + DATABASE_TABLE_NAME + " WHERE " + COLUMN_OrderId + " = '" + orderid + "'";
        db.execSQL(query);
    }

    public void deleteItemFromList(String orderid, String qty, String dishname)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String query = " DELETE " + " FROM " + DATABASE_TABLE_NAME + " WHERE " + COLUMN_OrderId + " = '" + orderid + "' AND " + COLUMN_DishName + " = '" + dishname + "' AND " + COLUMN_Quantity + " = '" + qty + "'";
        db.execSQL(query);
    }

}
