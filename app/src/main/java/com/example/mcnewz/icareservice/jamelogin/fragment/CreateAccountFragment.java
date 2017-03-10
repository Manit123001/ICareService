package com.example.mcnewz.icareservice.jamelogin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CreateAccountFragment extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    private EditText edtFirstNameCreate;
    private EditText edtLastNameCreate;
    private EditText edtPhoneCreate;
    private Button btnAccountCreate;
    String formattedData,IdFace,Iemai;
    String fname,lname,email,password,phone,code;

    public CreateAccountFragment() {
        super();
    }

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_account_login, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        //setRetainInstance(true);
        FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        IdFace = user.getUid();
        Iemai = user.getEmail();
        edtLastNameCreate = (EditText)rootView.findViewById(R.id.edtLastNameCreate);
        edtFirstNameCreate = (EditText)rootView.findViewById(R.id.edtFirstNameCreate);
        edtPhoneCreate = (EditText)rootView.findViewById(R.id.edtPhoneCreate);
        btnAccountCreate = (Button)rootView.findViewById(R.id.btnSignupCreate);
        //------Set Time Date------------------
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedData = df.format(c.getTime());


        btnAccountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private  void  verify(){
        if(edtFirstNameCreate.getText().toString().trim().isEmpty()){
            edtFirstNameCreate.setError("Invalid Firstname");
            edtFirstNameCreate.requestFocus();
        }else if(edtLastNameCreate.getText().toString().trim().isEmpty()){
            edtLastNameCreate.setError("Invalid Lastname");
            edtLastNameCreate.requestFocus();
        }else if(edtPhoneCreate.getText().toString().trim().isEmpty()){
            edtPhoneCreate.setError("Invalid Phone");
            edtPhoneCreate.requestFocus();
        }else if(edtPhoneCreate.getText().toString().trim().length() < 10){
            edtPhoneCreate.setError("Invalid Phone");
            edtPhoneCreate.requestFocus();
        }else {
            onButtonClick();
        }
    }
    private void  onButtonClick() {
        fname = edtFirstNameCreate.getText().toString();
        lname = edtLastNameCreate.getText().toString();
        phone = edtPhoneCreate.getText().toString();
        password = "facebook";
        email = Iemai;

        Random r = new Random();
        int i1 = r.nextInt(9999 - 1000 + 1) + 1000;
        code = String.valueOf(i1);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, config.URL_CODEVERIFY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                config.status_verify = 2;
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, VerifyFragment.newInstance(fname,lname,email,password,phone,IdFace))
                        .addToBackStack(null)
                        .commit();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Success",code);
                params.put("token_target", config.token);
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
