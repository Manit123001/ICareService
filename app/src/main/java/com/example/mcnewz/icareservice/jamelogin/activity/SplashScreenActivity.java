package com.example.mcnewz.icareservice.jamelogin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.mcnewz.icareservice.fragment.MainFragment;
import com.example.mcnewz.icareservice.jamelogin.manager.CheckNetwork;
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


        mAuth  = com.google.firebase.auth.FirebaseAuth.getInstance();
        mAuthListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    config.status = 2;
                    user_id = user.getUid();
                    if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                        // your get/post related code..like HttpPost = new HttpPost(url);
                        updatetoken();

                    } else {
                        // No Internet
                        Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
                    }


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
                if(loggedIn){
                    SharedPreferences sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    user_id = sp.getString(config.USERNAME_SHARED_PREF,"");

                    // CheckInternet
                    if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                        // your get/post related code..like HttpPost = new HttpPost(url);
                        updatetoken();
                    } else {
                        // No Internet
                        Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
                    }

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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void  updatetoken(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "SplashSreenERROR", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("member_id", user_id);
                params.put("member_token", config.token);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
