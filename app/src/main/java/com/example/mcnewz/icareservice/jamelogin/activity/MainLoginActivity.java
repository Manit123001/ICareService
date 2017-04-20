package com.example.mcnewz.icareservice.jamelogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.mcnewz.icareservice.jamelogin.fragment.MainFragment;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainLoginActivity extends AppCompatActivity {
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
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
                    userid = user.getUid();
                    updatetoken();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.contentContainer, new MainFragment(),"MainFragment")
                            .commit();


                }
            }
        };
//
//        if(savedInstanceState == null){
//
//            //First Created
//            //Place Fragment here
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, new MainFragment(),"MainFragment")
//                    .commit();
//
//        }
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(savedInstanceState == null){
            MainFragment fragment = (MainFragment)
                    getSupportFragmentManager().findFragmentByTag("MainFragment");
//            fragment.setHelloText("Woowo owowo");

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
                        //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("member_id", userid);
                params.put("member_token", config.token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
