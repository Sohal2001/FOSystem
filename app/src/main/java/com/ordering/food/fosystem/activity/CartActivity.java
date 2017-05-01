package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.activity.UserMainActivity;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CartActivity extends UserMainActivity {

    ArrayAdapter<String> adapter;
    static String[] strItems;
    ListView listv;
    TextView tvSubT,tvDelivery,tvTotal;
    String itemName;
    int qnty;
    int itemPrice;
    double subTotalPrice=0;
    double totalPrice=0;
    String user_email;
    DatabaseHelper myDb;
    SessionManager session;
    ArrayList<String> emails=new ArrayList<>();
    ArrayList<Integer> ccnums=new ArrayList<>();
    HashMap<String,String> orders=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_cart, contentFrameLayout);

        session=new SessionManager(getApplicationContext());
        myDb = new DatabaseHelper(this);

        HashMap<String,String> user = session.getUserDetails();
        user_email = user.get(session.KEY_EMAIL);

        tvSubT = (TextView)findViewById(R.id.tvSubTotalC);
        tvDelivery = (TextView)findViewById(R.id.tvDeliveryC);
        tvTotal = (TextView)findViewById(R.id.tvTotalC);


        getCartOrders();

        listv=(ListView)findViewById(R.id.listViewCart);

        adapter=new ArrayAdapter<String>(this,R.layout.list_veiw_resource,R.id.textview,strItems);
        listv.setAdapter(adapter);
    }
    public void getCartOrders(){
        Cursor res = myDb.getCartDetails();
        orders.put("email",user_email);
        if(res.getCount()>0){
            strItems = new String[res.getCount()];
            int i = 0;
            while(res.moveToNext()) {

                itemName = res.getString(0);
                qnty = res.getInt(1);
                itemPrice = res.getInt(2);
                subTotalPrice = subTotalPrice + res.getDouble(3);

                if (itemPrice < 10){
                    strItems[i] = qnty + " x " + " $0" + itemPrice + ".00 ,  " + itemName;
                    orders.put("order" + i, strItems[i]);
                }else{
                    strItems[i] = qnty + " x " + " $" + itemPrice + ".00 ,  " + itemName;
                    orders.put("order" + i, strItems[i]);
                }
                i++;

            }
            tvSubT.setText("$"+subTotalPrice);
            tvDelivery.setText("$0.0");
            totalPrice = subTotalPrice + 0;
            orders.put("total price",""+totalPrice);
            tvTotal.setText("$"+totalPrice);
//            orders.put("card",""+ccnums.get(0));

        }else {
            strItems = new String[1];
            strItems[0] = "No Items in the cart.";
            tvSubT.setText("");
            tvDelivery.setText("");
            tvTotal.setText("");
        }
    }
    public void placeOrder(View v){

        int count = myDb.getOrdersCount();
        int ordno = count+1;
        Cursor res = myDb.getCartDetails();

        if(res.getCount()>0){

            while(res.moveToNext()){
                itemName = res.getString(0);
                qnty = res.getInt(1);
                itemPrice = res.getInt(2);
                totalPrice = res.getDouble(3);
                myDb.insertData_Order(ordno,user_email,itemName,qnty,itemPrice,totalPrice);
            }
            new ProcessRegister().execute();
            System.out.println("-Card---2---------"+ccnums.size()+"---------------");
            if(ccnums.size()!=0){
                Toast.makeText(CartActivity.this, "Card is not added with this account please add one..", Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),AddCredit.class);
                startActivity(i);
            }else {
                System.out.println("------got user-------" + user_email + "-------");
                Toast.makeText(CartActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
                myDb.deleteCartOrder();

                getCartOrders();
                adapter = new ArrayAdapter<String>(this, R.layout.list_veiw_resource, R.id.textview, strItems);
                listv.setAdapter(adapter);

                Intent i=new Intent(getApplicationContext(),MyOrders.class);
                i.putExtra("data",orders);
                startActivity(i);

                System.out.println("==============="+orders.size()+"===================");

            }
        }
    }
    private class ProcessRegister extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject json=null;
            UserFunction userFunction = new UserFunction();
            try {
                System.out.println("<------in order2-33------->");
                json = userFunction.getCard(user_email);
                Log.e("Jsons - Array",json.toString());

                JSONArray jsarr=json.getJSONArray("uid");
                Log.e("Json Array",jsarr.toString());

                for (int i = 0; i < jsarr.length(); i++) {
                    JSONObject on = jsarr.getJSONObject(i);
                    System.out.println("homs-->"+on);
                    Iterator x=on.keys();
                    int j=0;
                    while(x.hasNext()){
                        String key=(String) x.next();
                        System.out.println("homs1-->"+key);
                        if(j==1){
                            ccnums.add(on.getInt(key));
                        }else  if(j==4) {
                            emails.add(on.getString(key));
                            j=0;
                        }
                        j++;
                    }
                }
//                int[] ccnolast=new int[4];
//                String no="";
//                int n=ccnums.get(0);
//                for(int i=0;i<ccnolast.length;i++){
//                    int mo=n%10;
//                    ccnolast[i]=mo;
//                    n=n/10;
//                }
//                for(int i=3;i>=0;i--){
//                    no+=ccnolast[i];
//                }
                orders.put("last ccno",ccnums.get(0)+"");
               //dscv 0.
                // System.out.println("-Card------------"+ccnums.size()+"----"+ccnums.get(0)+"-----"+ccnolast[4]+"------");
            } catch (JSONException e1) {
                System.out.println("-----order2......1-------------");
                e1.printStackTrace();
            } catch (Exception e) {
                System.out.println("-----order2......3-------------");
                e.printStackTrace();
            }
            return null;
        }

    }
}
