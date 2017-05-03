package com.example.mcnewz.icareservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProfileFragment extends Fragment {

    private ImageView ivPro;
    private TextView txtId;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtAddress;
    private EditText edtPhon;

    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String tel;
    private Button btnUpdate;
    private String username;

    public ProfileFragment() {
        super();
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        ivPro = (ImageView)rootView.findViewById(R.id.ivPrott);
        txtId = (TextView)rootView.findViewById(R.id.txtId);
        edtName = (EditText)rootView.findViewById(R.id.edtFirstName);
        edtLastName = (EditText)rootView.findViewById(R.id.edtLastName);
        edtAddress = (EditText)rootView.findViewById(R.id.edtAddress);
        edtPhon = (EditText)rootView.findViewById(R.id.edtPhone);


        new ProfileFragment.DownloadImageTask().execute(config.PhotoUserUpdate);


        SharedPreferences sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        txtId.setText(sp.getString("email","null"));
        edtName.setText(sp.getString("firstname","null"));
        edtLastName.setText(sp.getString("lastname","null"));
        edtAddress.setText(sp.getString("address","null"));
        edtPhon.setText(sp.getString("tel","null"));
        username = sp.getString("Idusername","null");
        Log.d("IDUSer",username);


        btnUpdate = (Button)rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
            }
        });

    }

    private void setData() {
        //loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_UPdatemember, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                getData();
                Toast.makeText(Contextor.getInstance().getContext(), "UpDate Success", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("firstname", edtName.getText().toString());
                params.put("lastname", edtLastName.getText().toString());
                params.put("tel", edtPhon.getText().toString());
                params.put("address",edtAddress.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void getData() {
        //loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.URL_DATA, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(config.USERNAME_SHARED, username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);

            firstname = collegeData.getString(config.READ_FIRSTNAME);
            lastname = collegeData.getString(config.READ_LASTNAME);
            email   = collegeData.getString(config.READ_EMAIL);
            address = collegeData.getString("address");
            tel = collegeData.getString("tel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // tvName.setText(firstname +" "+ lastname);
        // tvMail.setText(email);
        //ivPro.setImageResource();

        txtId.setText(email);
        edtName.setText(firstname);
        edtLastName.setText(lastname);
        edtAddress.setText(address);
        edtPhon.setText(tel);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tel",tel);
        editor.putString("address",address);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.apply();


    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                ivPro.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels / 100) * 24;
                ivPro.setImageBitmap(result);
            }
        }
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
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
