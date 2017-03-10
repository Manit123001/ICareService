package com.example.mcnewz.icareservice.jamelogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.mcnewz.icareservice.activity.MainActivity;
import com.example.mcnewz.icareservice.jamelogin.manager.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class VerifyFragment extends Fragment {

    private TextView txtNumberPhune;
    private EditText edtVerify;
    private Button btnVerify;
    private boolean loggedIn = false;

    String fname,lname,email,password,phone,IdFace;
    String formattedData;

    public VerifyFragment() {
        super();
    }

    public static VerifyFragment newInstance(String fname, String lname, String email, String password, String phone, String IdFace) {
        VerifyFragment fragment = new VerifyFragment();
        Bundle args = new Bundle();
        args.putString("fname",fname);
        args.putString("lname",lname);
        args.putString("email",email);
        args.putString("password",password);
        args.putString("phone",phone);
        args.putString("IdFace",IdFace);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fname = getArguments().getString("fname");
        lname = getArguments().getString("lname");
        email = getArguments().getString("email");
        password = getArguments().getString("password");
        phone = getArguments().getString("phone");
        IdFace = getArguments().getString("IdFace");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frament_verify_login, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        //setRetainInstance(true);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedData = df.format(c.getTime());
        txtNumberPhune = (TextView)rootView.findViewById(R.id.txtNumberPhone);
        txtNumberPhune.setText(phone);
        edtVerify = (EditText)rootView.findViewById(R.id.edtVerify);
        btnVerify = (Button)rootView.findViewById(R.id.btnVerify);
        Log.d("Test",fname+":"+lname+":"+email+":"+password+":"+phone+":"+IdFace+":"+formattedData+":"+ config.token);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtVerify.getText().toString().trim().equals(config.idcode)){
                    if (config.status_verify == 1){
                        onRegister();
                    }else {
                        onRegisterFace();
                    }

                }else {

                    Toast.makeText(getContext(), config.idcode,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void onRegister(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, config.REGIS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Toast.makeText(getContext(), "เพิ่มข้อมูลRegis", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, SignInFragment.newInstance())
                        .commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError", error.toString());
                Toast.makeText(getContext(), "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(config.INSERT_FIRSTNAME, fname);
                params.put(config.INSERT_LASTNAME, lname);
                params.put(config.INSERT_EMAIL, email);
                params.put(config.INSERT_PASSWORD, password);
                params.put(config.INSERT_TEL, phone);
                params.put(config.INSERT_DATA, formattedData);
                params.put(config.INSERT_TOKEN, config.token);

                return params;
            }
        };
        requestQueue.add(request);
    }
    private void onRegisterFace(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, config.REGIS_FACEBOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onError", error.toString());
                Toast.makeText(getContext(), "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(config.INSERT_FIRSTNAME, fname);
                params.put(config.INSERT_LASTNAME, lname);
                params.put(config.INSERT_IDFACE,IdFace);
                params.put(config.INSERT_EMAIL,email);
                params.put(config.INSERT_PASSWORD, password);
                params.put(config.INSERT_TEL, phone);
                params.put(config.INSERT_DATA, formattedData);
                params.put(config.INSERT_TOKEN, config.token);
                return params;
            }
        };
        requestQueue.add(request);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
