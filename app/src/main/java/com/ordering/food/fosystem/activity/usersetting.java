package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ordering.food.fosystem.R;

public class usersetting extends UserMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_usersetting, contentFrameLayout);

        Button basic=(Button)findViewById(R.id.btnbasic);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),basicinfo.class);
                startActivity(i1);
            }
        });

        Button addcard=(Button)findViewById(R.id.btnaddcard);
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),AddCredit.class);
                startActivity(i2);
            }
        });
    }
}
