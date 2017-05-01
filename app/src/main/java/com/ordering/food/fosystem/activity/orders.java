package com.ordering.food.fosystem.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ordering.food.fosystem.R;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class orders extends UserMainActivity {

//    GetNearbyPlacesData restaurants=new GetNearbyPlacesData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_orders, contentFrameLayout);

        final ListView hotel=(ListView)findViewById(R.id.hotels);
        boolean flag= getIntent().getBooleanExtra("flag",false);

//        Set<String> keys = GetNearbyPlacesData.data.keySet();
//        for(String key:keys){
//            System.out.println("------------------"+key+"---------------------");
//        }
        Collection<String> vals = GetNearbyPlacesData.data.values();
        String[] array = vals.toArray(new String[vals.size()]);
//        for(String key:vals){
//            System.out.println("------------------"+key+"---------------------");
//        }
        if(flag){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,array);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hotel.setAdapter(adapter);
        }

        hotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String foodstore=(String)hotel.getItemAtPosition(position);
                Intent i=new Intent(getApplicationContext(),ordermain.class);
                i.putExtra("index",position+1);
                i.putExtra("restaurant",foodstore);
                startActivity(i);
            }
        });

    }
}
