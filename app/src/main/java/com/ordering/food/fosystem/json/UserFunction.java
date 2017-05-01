package com.ordering.food.fosystem.json;

import android.widget.Spinner;

import com.ordering.food.fosystem.activity.RegisterFragment;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserFunction {

    JSONObject json;
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private static String register_tag = "register";
    private JSONParser jsonParser;
    private static String registerURL = "http://192.168.56.1/fos/register.php";
    private static String registerCardURL = "http://192.168.56.1/fos/registerCard.php";
    private static String registerInfoURL = "http://192.168.56.1/fos/storeUserInfo.php";
    private static String getCard = "http://192.168.56.1/fos/getCard.php";
    private static String loginURL = "http://192.168.56.1/fos/login.php";
    private static String getMenuURL="http://192.168.56.1/fos/getMenu.php";



    public UserFunction() {
        jsonParser = new JSONParser();
    }

    public JSONObject getCard(String email){
        List params = new ArrayList();

        params.add(new BasicNameValuePair("email", email));

        JSONObject json = jsonParser.getCardJSONFromUrl(getCard, params);

        return json;
    }

    public JSONObject registerUser(String name, String email, String password) throws IOException {

        List params = new ArrayList();

        params.add(new BasicNameValuePair("name", name));

        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);

        return json;
    }
    public JSONObject registerCard(String email,String ccnumber,String ccexp,String cvvnumber) throws IOException {

        List params = new ArrayList();

        params.add(new BasicNameValuePair("email", email));

        params.add(new BasicNameValuePair("ccnumber", ccnumber));
        params.add(new BasicNameValuePair("ccexp", ccexp));
        params.add(new BasicNameValuePair("cvvnumber", cvvnumber));
        JSONObject json = jsonParser.getCardJSONFromUrl(registerCardURL, params);

        return json;
    }

    public JSONObject addBasicInfo(String email,String Address,String phNo) throws IOException {

        List params = new ArrayList();

        params.add(new BasicNameValuePair("email", email));

        params.add(new BasicNameValuePair("address", Address));
        params.add(new BasicNameValuePair("contactno", phNo));
        JSONObject json = jsonParser.getBasicInfoJSONFromUrl(registerInfoURL, params);

        return json;
    }
    public String  getMenu(int ordertime,int resId,int foodtype,int foodcategory) throws IOException {

        System.out.print("get menu------------->1");
        List params = new ArrayList();

        System.out.print("get menu------------->2");
        params.add(new BasicNameValuePair("ordertime",ordertime+""));
        params.add(new BasicNameValuePair("resId",resId+""));
        params.add(new BasicNameValuePair("foodtype",foodtype+""));
        params.add(new BasicNameValuePair("foodcategory",foodcategory+""));

        System.out.print("get menu------------->3");
        String json = jsonParser.getMenuFromUrl(getMenuURL, params);

        System.out.print("get menu------------->4");

        return json;
    }


    public JSONObject loginUser(String email, String password) {

        List params = new ArrayList();

        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        JSONObject json = jsonParser.getUserLogin(loginURL, params);
        return json;
    }
}
