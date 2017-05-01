package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ordermain extends UserMainActivity {

    String id="";
    ArrayList<JSONObject> jsons=new ArrayList<>();
    public Spinner foodType;
    public Spinner foodCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_ordermain, contentFrameLayout);

        final RadioGroup rg=(RadioGroup)findViewById(R.id.radioTime);

        final int indexOfRestaurant=getIntent().getIntExtra("index",1);
        String restaurantName= getIntent().getStringExtra("restaurant");
        System.out.print("--------------------------"+restaurantName);

        TextView restaurant=(TextView) findViewById(R.id.txtrestaurant);
        restaurant.setText(restaurantName);

        final RadioButton lunch=(RadioButton)findViewById(R.id.radioButtonlunch);
        final RadioButton dinner=(RadioButton)findViewById(R.id.radioButtonDinner);

        foodType=(Spinner) findViewById(R.id.spinnerType);
        String[] array= new String[]{"Vegeterian", "Non-Vegeterian", "Vegan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodType.setAdapter(adapter);

        foodCategory=(Spinner) findViewById(R.id.spinnerCategory);
        String[] array1= new String[]{"Indian", "Chinese","American", "Mexican", "French","Russian"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,array1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodCategory.setAdapter(adapter1);


        final Button next=(Button) findViewById(R.id.btnNextToOrder);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int typeId=(int)foodType.getSelectedItemId()+1;
                final int categoryId=(int)foodCategory.getSelectedItemId()+1;
                Intent i = new Intent(getApplicationContext(), orders2.class);
                if(lunch.isChecked()) {
                    System.out.print("in Lunch------------>");
                    i.putExtra("categoryType",categoryId);
                    i.putExtra("indexOfRestaurant", indexOfRestaurant);
                    i.putExtra("type", typeId);
                    i.putExtra("ordertime", 1);
                }
                else if(dinner.isChecked()) {
                    System.out.print("in Dinner ------------>");
                    i.putExtra("categoryType",categoryId);
                    i.putExtra("indexOfRestaurant", indexOfRestaurant);
                    i.putExtra("type", typeId);
                    i.putExtra("ordertime", 2);
                }
                startActivity(i);
            }
        });
    }

}
