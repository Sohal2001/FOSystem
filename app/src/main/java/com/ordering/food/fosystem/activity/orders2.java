package com.ordering.food.fosystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;



public class orders2 extends UserMainActivity {

    int ordertime;
    int resId;
    int foodtype;
    int foodcategory;
    ArrayList<String> itemname=new ArrayList<>();
    ArrayList<Integer> price=new ArrayList<>();
    ArrayList<String> newPrice=new ArrayList<>();
    ArrayList<Integer> menuId=new ArrayList<>();
    static ListView menu=null;
    static ListView lvPrice=null;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;

    int countCart=0;
    DatabaseHelper myDb;
    String qtystr;
    int qnty;
    Button cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_orders2, contentFrameLayout);

        myDb=new DatabaseHelper(this);
        System.out.print("====================>starting process Register 1=============");

        Toast.makeText(this, "In the order of lunch page..!!!", Toast.LENGTH_LONG).show();
        resId=getIntent().getIntExtra("indexOfRestaurant",1);
        foodtype=getIntent().getIntExtra("type",1);
        foodcategory=getIntent().getIntExtra("categoryType",1);
        ordertime=getIntent().getIntExtra("ordertime",1);
        System.out.print("============"+foodcategory+"========>starting "+ordertime+" Register 2=============");

        menu=(ListView)findViewById(R.id.listMenu);
        lvPrice=(ListView) findViewById(R.id.list);
        myDb.deleteCartOrder();
        System.out.println("====================>starting process Register 3=============");
        new ProcessRegister().execute();
        System.out.println("============================>Ending process Register 4========");

        cart=(Button)findViewById(R.id.btnNextToCheckout);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
            }
        });

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                alertQnty(i);
            }
        });
        countCart = myDb.getCartCount();
        if(countCart > 0)
            cart.setText("CART("+countCart+")");
        else
            cart.setText("CART");


    }

    public void alertQnty(final int i){

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setCancelable(false);
        alert.setMessage("Quantity?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                qtystr = input.getText().toString().trim();
                qnty = Integer.parseInt(qtystr);

                double totalPrice = qnty*price.get(i);
                boolean isInserted = myDb.insertData_Cart(itemname.get(i),qnty,price.get(i),totalPrice);

                if (isInserted) {
                    Toast.makeText(orders2.this, itemname.get(i)+" added", Toast.LENGTH_LONG).show();
                    countCart = countCart+1;
                    cart.setText("CART("+countCart+")");
                } else{
                    Toast.makeText(orders2.this, "Not added", Toast.LENGTH_LONG).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();

            }
        });
        alert.show();

    }


    private class ProcessRegister extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params) {

            String json=null;
            UserFunction userFunction = new UserFunction();
            try {
                System.out.println("<------in order2-------->");
                json = userFunction.getMenu(ordertime,resId,foodtype,foodcategory);
                System.out.println("<------in order2-------->-----"+json+"-----json");

                JSONObject jObj=new JSONObject(json);
                System.out.println("<------in order2-------->"+jObj+"-----json-----");

                JSONArray jsarr=jObj.getJSONArray("uid");
                Log.e("Json Array",jsarr.toString());
                for (int i = 0; i < jsarr.length(); i++) {
                    JSONObject on = jsarr.getJSONObject(i);
                    System.out.println("hom-->"+on);
                    Iterator x=on.keys();
                    int j=0;
                    while(x.hasNext()){
                        String key=(String) x.next();
                        System.out.println("hom1-->"+key);
                        if(j==0){
                            menuId.add(on.getInt(key));
                            j++;
                        }else  if(j==1) {
                            itemname.add(on.getString(key));
                            j++;
                        }else if(j==2) {
                            price.add(on.getInt(key));
                            j=0;
                        }
                    }
                }
                String s="$";
                for(int i=0;i<price.size();i++){
                    newPrice.add(s+price.get(i));
                    System.out.println("new Price-->"+newPrice.get(i));
                }
                System.out.println("---"+newPrice.size()+"----");
                adapter = new ArrayAdapter<>(orders2.this,R.layout.support_simple_spinner_dropdown_item,itemname);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                adapter1 = new ArrayAdapter<>(orders2.this,R.layout.support_simple_spinner_dropdown_item,newPrice);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                orders2.menu.setAdapter(adapter);
                orders2.lvPrice.setAdapter(adapter1);
                cart.setText("Cart");

            } catch (JSONException e1) {
                System.out.println("-----order2......1-------------");
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println("-----order2.....2.-------------");
                e1.printStackTrace();
            } catch (Exception e) {
                System.out.println("-----order2......3-------------");
                e.printStackTrace();
            }
            return null;
        }

    }
}