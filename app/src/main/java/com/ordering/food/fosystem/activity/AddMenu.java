package com.ordering.food.fosystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ordering.food.fosystem.R;

public class AddMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        EditText address=(EditText)findViewById(R.id.menuitem);
        EditText contact=(EditText)findViewById(R.id.itemprice);

        Button save=(Button)findViewById(R.id.btnsavemenu);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
