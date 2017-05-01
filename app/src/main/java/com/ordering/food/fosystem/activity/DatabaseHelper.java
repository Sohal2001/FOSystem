package com.ordering.food.fosystem.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "foodordering.db";

    SQLiteDatabase db;

    public static final String TABLE_NAME2 = "ORDERS_TABLE";
    public static final String OCOL1 = "ORDER_NUMBER";
    public static final String OCOL2 = "EMAIL";
    public static final String OCOL3 = "ITEM";
    public static final String OCOL4 = "QUANTITY";
    public static final String OCOL5 = "ITEM_PRICE";
    public static final String OCOL6 = "TOTAL_PRICE";
    public static final String OCOL7 = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME2+"(ORDER_NUMBER INTEGER,EMAIL TEXT NOT NULL," +
                "ITEM TEXT,QUANTITY INTEGER,ITEM_PRICE INTEGER,TOTAL_PRICE DOUBLE,DATE DATETIME DEFAULT CURRENT_TIMESTAMP)");
        db.execSQL("create table CART_TABLE(ITEM TEXT,QUANTITY INTEGER,ITEM_PRICE INTEGER,TOTAL_PRICE DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS CART_TABLE");
        onCreate(db);
    }

    public boolean insertData_Order(int ordno, String email, String item, int quantity, int price, double total){
        db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OCOL1,ordno);
        contentValues.put(OCOL2,email);
        contentValues.put(OCOL3,item);
        contentValues.put(OCOL4,quantity);
        contentValues.put(OCOL5,price);
        contentValues.put(OCOL6,total);

        long result =  db.insert(TABLE_NAME2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getOrdersOnOrdno(int ordno){
        db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("select ITEM,QUANTITY,ITEM_PRICE,TOTAL_PRICE from ORDERS_TABLE where ORDER_NUMBER='"+ordno+"'",null);
        return res;
    }
    public Cursor getOrdersOnEmail(String e){
        db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("select ORDER_NUMBER,TOTAL_PRICE,DATE from ORDERS_TABLE where EMAIL='"+e+"'",null);
        return res;
    }
    public int getOrdersCount(){
        db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("select ORDER_NUMBER from ORDERS_TABLE",null);

        if(res.getCount()>0){
            res.moveToLast();
            return res.getInt(0);
        }else
            return 0;
    }

    public boolean insertData_Cart(String item, int quantity, int itemPrice, double totalPrice){
        db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ITEM",item);
        contentValues.put("QUANTITY",quantity);
        contentValues.put("ITEM_PRICE",itemPrice);
        contentValues.put("TOTAL_PRICE",totalPrice);

        long result =  db.insert("CART_TABLE",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getCartDetails(){
        db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from CART_TABLE",null);
        return res;
    }
    public int getCartCount(){
        db =  this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) from CART_TABLE",null);

        if(res.getCount()>0){
            res.moveToFirst();
            return res.getInt(0);
        }else
            return 0;
    }
    public void deleteCartOrder(){
        db =  this.getWritableDatabase();
        db.delete("CART_TABLE",null,null);
       //db.execSQL("delete from CART_TABLE");
    }
}
