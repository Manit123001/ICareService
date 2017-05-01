package com.example.mcnewz.icareservice.jamelogin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.activity.MainActivity;
import com.example.mcnewz.icareservice.manager.CheckNetwork;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.HashMap;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    long time = 3000L;
    long delay_time;
    String user_id;

    private boolean loggedIn = false;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        final String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("token555", token);

        config.token = token;


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
                if(loggedIn){
                    SharedPreferences sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    user_id = sp.getString(config.USERNAME_SHARED_PREF,"null");


                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                }else {

                    Intent intent = new Intent(getApplicationContext(),MainLoginActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        delay_time =time;
        handler.postDelayed(runnable,delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis()-time);
    }

    /****************
     * Inner Class
     *****************/


}
