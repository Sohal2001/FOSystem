package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class basicinfo extends UserMainActivity {
    String email;
    String Address;
    String phNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_basicinfo, contentFrameLayout);

        final EditText address=(EditText)findViewById(R.id.address);
        final EditText contact=(EditText)findViewById(R.id.phno);

        Button save=(Button)findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address= address.getText().toString().trim();
                phNo=contact.getText().toString().trim();
                new ProcessRegister().execute();

                Intent i=new Intent(getApplicationContext(),UserMainActivity.class);
                startActivity(i);
            }
        });
    }

    private class ProcessRegister extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            System.out.println("-------Preexecute add credit  2-----");
            super.onPreExecute();
            HashMap<String, String> user = session.getUserDetails();
            email = user.get(session.KEY_EMAIL);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            System.out.println("-------Post execute add credit 2-------");
            try {
                String error = json.getString("error");
                if (error.equals("false")) {
                    String id = json.getString("id");
                    System.out.println("id"+id);

                    JSONObject user = (JSONObject) json.get("user");
                    Toast.makeText(getApplicationContext(), "Basic Info is stored to user "+user.getString("email"), Toast.LENGTH_LONG).show();

                } else {
                    String errorMsg = json.getString("error_msg");
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            UserFunction userFunction = new UserFunction();
            JSONObject json = null;
            try {
                json = userFunction.addBasicInfo(email,Address,phNo);
                Log.e("JSON by me ",json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

    }
}
