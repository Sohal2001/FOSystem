package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;
import com.ordering.food.fosystem.json.UserFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sohal on 15-02-2017.
 */

public class RegisterFragment extends Fragment {
    View MyView;

    private Button btnRegister;
    private EditText fullName;
    private EditText emailID;
    private EditText inputPassword;
    private EditText confPassword;

    private String name;
    private String email;
    private String password;
    private String cPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView=inflater.inflate(R.layout.register_activity,container,false);

        fullName = (EditText) MyView.findViewById(R.id.txtName);
        emailID = (EditText) MyView.findViewById(R.id.txtEmail);
        inputPassword = (EditText) MyView.findViewById(R.id.txtPassword);
        confPassword = (EditText) MyView.findViewById(R.id.txtconfPassword);

        btnRegister =   (Button)   MyView.findViewById(R.id.registerGo);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name = fullName.getText().toString().trim();
                email = emailID.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                cPassword = confPassword.getText().toString().trim();

                if (!validationCheck()) {
                    System.out.print(">>>>>>>>>>>>1"+validationCheck());
                    Toast.makeText(getContext(), "Please Fill Form again...", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.print(">>>>>>>>>>>>2"+validationCheck());
                    new ProcessRegister().execute();
                }
            }
        });
        return MyView;
    }

    private boolean validationCheck() {
        boolean valid = true;
        if (name.isEmpty() || name.length() > 32) {
            fullName.setError("Please Enter Valid Name");
            valid = false;
        }
        else{
            valid =true;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailID.setError("Please Enter Valid Email Address");
            valid = false;
        }
        else{
            valid =true;
        }
        if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Please Enter Valid Password");
            valid = false;
        }
        else{
            valid =true;
        }
        if (!cPassword.equals(password)) {
            confPassword.setError("Please Enter Same password");
            valid = false;
        }
        else{
            valid =true;
        }
        return valid;
    }


    private class ProcessRegister extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                String error = json.getString("error");

                if (error.equals("false")) {

                    String id = json.getString("id");
                    System.out.println("id"+id);

                    JSONObject user = (JSONObject) json.get("user");

                    String email =(String)user.get("email");
                    String name = (String) user.get("name");

                    SessionManager session1 = new SessionManager(getContext());
                    Toast.makeText(getContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                    session1.createLoginSession(name, email,id);

                    Intent i = new Intent(getContext(),UserMainActivity.class);
                    startActivity(i);

                } else {
                    String errorMsg = json.getString("error_msg");
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
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
                json = userFunction.registerUser(name, email, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}
