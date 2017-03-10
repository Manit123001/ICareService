package com.example.mcnewz.icareservice.activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.graphics.Color;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.support.transition.Transition;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.fragment.MainFragment;
import com.example.mcnewz.icareservice.jamelogin.activity.MainLoginActivity;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private NavigationView navigationView;
    private TextView tvName;
    private TextView tvMail;

    private String firstname="";
    private String lastname ="";
    private String email = "";

    private ProgressDialog loading;
    String user_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment here !
        if (savedInstanceState == null) {
            //initInstances();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
        initToolbar();
//        initInstances();

    }
//    private void initInstances() {
////        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
//  //      tvName = (TextView)headerLayout.findViewById(R.id.tvName);
////        tvMail = (TextView)headerLayout.findViewById(R.id.tvMail);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        SharedPreferences sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        user_id = sp.getString(config.USERNAME_SHARED_PREF,"");
//        //Initializing textview
//        if(config.status == 1){
//            user_id = sp.getString(config.USERNAME_SHARED_PREF,"");
//        }else {
//            if (user != null) {
//                user_id = user.getUid();
//            }
//        }
//        getData();
//    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    // Toolbar here
    private void initToolbar() {
        // setActionbar Toolbar

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // drawer layout Here
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(
//                MainActivity.this,
//                drawerLayout,
//                R.string.open_drawer,
//                R.string.close_drawer
//        );
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }



}
