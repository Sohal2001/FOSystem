package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrders extends UserMainActivity{
    ListView listv;
    ArrayAdapter<String> adapter;
    static ArrayList<String> strItems=new ArrayList<>();
    HashMap<String, String> myorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_account, contentFrameLayout);
        if(getIntent().hasExtra("data")) {
            myorder = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        System.out.println("==========123====="+myorder.size()+"===================");

        for (Map.Entry<String, String> entry : myorder.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("email")){
                strItems.add("Order with email : "+value);
            }
            else if(key.equals("total price")) {
                strItems.add("Total Amount : $" + value);
            }
            else{
                strItems.add(value);
            }
            System.out.println("----key:"+key+"--------value:"+value+"-------------");
        }
        }
        listv=(ListView)findViewById(R.id.listViewOrders);

        adapter=new ArrayAdapter<String>(this,R.layout.list_veiw_resource,R.id.textview,strItems);
        listv.setAdapter(adapter);
    }
}
