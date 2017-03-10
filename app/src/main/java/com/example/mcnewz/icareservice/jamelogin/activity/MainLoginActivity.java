package com.example.mcnewz.icareservice.jamelogin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.activity.MainActivity;
import com.example.mcnewz.icareservice.jamelogin.fragment.MainFragment;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainLoginActivity extends AppCompatActivity {
    private boolean loggedIn = false;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("token555", token);

        config.token = token;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        mAuth  = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    config.status = 2;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        };

        if(savedInstanceState == null){

            //First Created
            //Place Fragment here
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, new MainFragment(),"MainFragment")
                    .commit();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(savedInstanceState == null){
            MainFragment fragment = (MainFragment)
                    getSupportFragmentManager().findFragmentByTag("MainFragment");
//            fragment.setHelloText("Woowo owowo");

        }
    }
}
