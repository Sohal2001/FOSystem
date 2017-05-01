
package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class AddCredit extends UserMainActivity {

    SessionManager session;


    private EditText creditNumber;
    private EditText creditExp;
    private EditText creditCVV;
    private Button addCredit;

    private String ccnumber;
    private String ccexp;
    private String cvvnumber;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout contentFrameLayout = (RelativeLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.add_credit, contentFrameLayout);

        session = new SessionManager(getApplicationContext());

        System.out.println("-----INSIDE THE PAYMENT----");
        creditNumber = (EditText) findViewById(R.id.ccnumber);
        creditExp = (EditText) findViewById((R.id.expdate));
        creditCVV = (EditText) findViewById((R.id.cvv));

        addCredit = (Button) findViewById(R.id.addCreditCard);

        addCredit.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                ccnumber = creditNumber.getText().toString().trim();
                ccexp = creditExp.getText().toString().trim();
                cvvnumber = creditCVV.getText().toString().trim();

                if (!validationCheck()){
                    System.out.print(">>>>>>>>>>>>1"+validationCheck());
                    Toast.makeText(getBaseContext(),"Please Fill Form again...", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "Please Fill Form again...", Toast.LENGTH_SHORT).show();
                }
                else {
                    new ProcessRegister().execute();
                    Intent i = new Intent(getApplicationContext(), UserMainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private boolean validationCheck() {
        boolean valid = true;
        if (ccnumber.isEmpty() || ccnumber.length() != 16) {
            creditNumber.setError("Please Enter Valid 16 digit Credit Card Number");
            valid = false;
        }
        else{
            valid =true;
        }

        if (ccexp.isEmpty() || ccexp.length() != 4) {
            creditExp.setError("Please Enter Valid 4 digit Expiration Date");
            valid = false;
        }
        else{
            valid =true;
        }

        if (cvvnumber.isEmpty()||cvvnumber.length() != 3) {
            creditCVV.setError("Please Enter Valid 3 or 4 CVV Number");
            valid = false;
        }
        else{
            valid =true;
        }

        return valid;
    }

    private class ProcessRegister extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        protected void onPreExecute() {
            System.out.println("----ccnumber---"+ccnumber);
            System.out.println("----ccexp---"+creditExp);
            System.out.println("----cvv---"+cvvnumber);

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
                    Toast.makeText(getApplicationContext(), "Credit Card Registered with "+user.getString("email"), Toast.LENGTH_LONG).show();

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
                json = userFunction.registerCard(email,ccnumber,ccexp,cvvnumber);
                Log.e("JSON by me ",json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

    }
}

