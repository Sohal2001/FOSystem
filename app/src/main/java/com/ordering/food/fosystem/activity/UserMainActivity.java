package com.ordering.food.fosystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.ordering.food.fosystem.R;
import com.ordering.food.fosystem.helper.SessionManager;

import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;

    TextView username;
    TextView usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_user_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_user_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        HashMap<String,String> user = session.getUserDetails();

        username=(TextView) header.findViewById(R.id.username);
        usermail=(TextView) header.findViewById(R.id.useremail);

        String email = user.get(session.KEY_EMAIL);
        String uname = user.get(session.KEY_NAME);
        String fname=user.get(session.KEY_FNAME);
        String email2=user.get(session.KEY_EMAIL2);
        System.out.print("==============usermainActivity======="+email2);
        System.out.print("==============usermainActivity======="+fname);
        if(uname==null) {
            username.setText(fname);
            usermail.setText(Html.fromHtml("<u>" + email2 + "</u>"));
        }
        else{
            username.setText(uname);
            usermail.setText(Html.fromHtml("<u>" + email + "</u>"));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.search_restaurant:
                Intent i1 = new Intent(getApplicationContext(),UserMapsActivity.class);
                startActivity(i1);
                break;

            case R.id.myorders:
                Intent i4 = new Intent(getApplicationContext(),MyOrders.class);
                startActivity(i4);
                break;

            case R.id.settings:
                Intent i5 = new Intent(getApplicationContext(),usersetting.class);
                startActivity(i5);
                break;

            case R.id.logout:
                LoginManager.getInstance().logOut();
                session.logoutUser();
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
