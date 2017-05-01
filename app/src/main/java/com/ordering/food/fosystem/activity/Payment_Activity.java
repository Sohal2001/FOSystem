
package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Payment_Activity extends UserMainActivity {

    SessionManager session;


    private EditText new_payment;
    private EditText getNew_payment;
    private EditText ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.payment, contentFrameLayout);

        System.out.println("-----INSIDE THE PAYMENT 1234----");
        //new ProcessRegister().execute();

        new_payment = (EditText) findViewById(R.id.credit_info);
        getNew_payment = (EditText) findViewById(R.id.new_payment);


        getNew_payment.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),
                        AddCredit.class);
                startActivity(i);
                finish();
            }
        });

    }








    private class ProcessRegister extends AsyncTask<String, Void, JSONObject>

    {

        private String id;


        @Override
        protected void onPreExecute() {

            session = new SessionManager(getApplicationContext());

            System.out.println("-------Preexecute  2-----");
            super.onPreExecute();
            HashMap<String, String> user = session.getUserDetails();
            id = user.get(session.KEY_ID);
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            System.out.println("-------Post execute 2-------");


            try {
                String error = json.getString("error");


                if (error.equals("false")) {


                    String id = json.getString("id");
                    session = new SessionManager(getApplicationContext());


                    System.out.println("id" + id);

                    JSONObject user = (JSONObject) json.get("user");

                    Intent i = new Intent(getApplicationContext(),
                            UserMainActivity.class);
                    startActivity(i);
                    finish();
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

            System.out.println("-------do IN background    2-----");
            UserFunction userFunction = new UserFunction();
            JSONObject json = null;
            try {
                //json = userFunction.getCreditInfo(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return json;


        }

    }
}

