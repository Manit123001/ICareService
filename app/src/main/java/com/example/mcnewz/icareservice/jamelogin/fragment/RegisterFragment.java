package com.example.mcnewz.icareservice.jamelogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.mcnewz.icareservice.activity.MainActivity;
import com.example.mcnewz.icareservice.manager.CheckNetwork;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RegisterFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    private EditText edtFirstNameRegister,edtLastNameRegister,edtEmailRegister,edtPasswordRegister,edtPhoneRegister;
    private Button btnSignupRegister;
    String fname,lname,email,password,phone,code,IdFace;


    String uID;
    private static final String TAG = "FacebookLoginActivity";
    private CallbackManager mCallbackManager;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private LoginButton loginButton;
    private Button btnFacebook;



    private SignInButton signInButton;
    private static final String TAGG = "GoogleSignInActivity";
    private static  int RC_SIGN_IN = 9000;
    private GoogleApiClient mGoogleApiClient;
    private  Button btnGoogle;
    private String idUser;
    private String address;
    private String firstname;
    private String lastname;
    private String temail;


    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_login, container, false);
        initInstances(rootView);

        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        //setRetainInstance(true);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();


        edtFirstNameRegister = (EditText)rootView.findViewById(R.id.edtFirstNameRegister);
        edtLastNameRegister = (EditText)rootView.findViewById(R.id.edtLastNameRegister);
        edtEmailRegister = (EditText)rootView.findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = (EditText)rootView.findViewById(R.id.edtPasswordRegister);
        edtPhoneRegister = (EditText)rootView.findViewById(R.id.edtPhoneRegister);
        btnSignupRegister = (Button)rootView.findViewById(R.id.btnSignupRegister);



        //------Set Time Date------------------



        btnSignupRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CheckInternet
                if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                    verify();

                } else {
                    // No Internet
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------------------------------------

        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button_facebook_regis);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        // If using in a fragment

        // Other app specific specialization
        // Callback registration
        btnFacebook = (Button)rootView.findViewById(R.id.btnFacebook_regis);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                    loginButton.performClick();
                    loginButton.setPressed(true);
                    loginButton.invalidate();
                    loginButton.registerCallback(mCallbackManager, mCallBack);
                    loginButton.setPressed(false);
                    loginButton.invalidate();
                } else {
                    // No Internet
                    Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
                }

            }
        });




        mAuth  = com.google.firebase.auth.FirebaseAuth.getInstance();

        mAuthListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uID = user.getUid();
                    config.status = 2;
                    checkFacebook();
                }

            }
        };
        //---------------------Google--------------------------------------------------
//        signInButton = (SignInButton)rootView.findViewById(R.id.sign_in_button_G);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
        btnGoogle = (Button)rootView.findViewById(R.id.btnGoogle_regis);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                    signIn();

                } else {
                    // No Internet
                }
            }
        });
    }

    //----------Facebook Start--------------------------------------
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            handleFacebookAccessToken(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                }

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                //updateUI(null);
            }
        }
    }
    //---------------------------Google----------------------------------------------------
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAGG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAGG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {

                } else {
                }
            }
        });
    }
    private void signIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .enableAutoManage(this.getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    //-----------------------------------------------------------------------------------------
    private void checkFacebook(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.CHECKFACEBOOK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.trim().toString().equalsIgnoreCase(config.LOGIN_SUCCESS)){
                            //ถ้าเคยSignแล้ว
                            updatetoken();

                            getData();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }else{
                            //ถ้ายังไม่เคยSign
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(
                                            R.anim.from_right,R.anim.to_left,
                                            R.anim.from_left,R.anim.to_right
                                    )
                                    .replace(R.id.contentContainer, CreateAccountFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(config.KEY_IDFACE, uID);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



    //----------Facebook End--------------------------------------




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

    private void  onButtonRegister() {
        fname = edtFirstNameRegister.getText().toString();
        lname = edtLastNameRegister.getText().toString();
        password = edtPasswordRegister.getText().toString();
        email = edtEmailRegister.getText().toString();
        phone = edtPhoneRegister.getText().toString();
        Random r = new Random();
        int i1 = r.nextInt(9999 - 1000 + 1) + 1000;
        code = String.valueOf(i1);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, config.URL_CODEVERIFY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, VerifyFragment.newInstance(fname,lname,password,email,phone,IdFace))
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
    //--------------------Check Email--------------------------------------

    //Return true if email is valid and false if email is invalid
    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    private  void  verify(){
        if(edtFirstNameRegister.getText().toString().trim().isEmpty()){
            edtFirstNameRegister.setError("Invalid Firstname");
            edtFirstNameRegister.requestFocus();
        }else if(edtLastNameRegister.getText().toString().trim().isEmpty()){
            edtLastNameRegister.setError("Invalid Lastname");
            edtLastNameRegister.requestFocus();
        }else if(edtEmailRegister.getText().toString().trim().isEmpty()){
            edtEmailRegister.setError("Invalid Email");
            edtEmailRegister.requestFocus();
        }else if(!validateEmail(edtEmailRegister.getText().toString())){
            edtEmailRegister.setError("Invalid Email");
            edtEmailRegister.requestFocus();
        }else if(edtPasswordRegister.getText().toString().trim().isEmpty()){
            edtPasswordRegister.setError("Invalid Password");
            edtPasswordRegister.requestFocus();
        }else if(edtPasswordRegister.getText().toString().trim().length() < 4){
            edtPasswordRegister.setError("Invalid Password");
            edtPasswordRegister.requestFocus();
        }else if(edtPhoneRegister.getText().toString().trim().isEmpty()){
            edtPhoneRegister.setError("Invalid Phone");
            edtPhoneRegister.requestFocus();
        }else if(edtPhoneRegister.getText().toString().trim().length() < 10){
            edtPhoneRegister.setError("Invalid Phone");
            edtPhoneRegister.requestFocus();
        }else {
            onButtonRegister();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    //--------------------Check Email End--------------------------------------

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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("member_id", uID);
                params.put("member_token", config.token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    //TODO Edit GetData
    private void getData() {

        //loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,config.URL_DATA, new com.android.volley.Response.Listener<String>() {
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
                params.put(config.USERNAME_SHARED, uID);
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


            idUser = collegeData.getString("member_id");
            phone = collegeData.getString("tel");
            address = collegeData.getString("address");
            firstname = collegeData.getString(config.READ_FIRSTNAME);
            lastname = collegeData.getString(config.READ_LASTNAME);
            temail   = collegeData.getString(config.READ_EMAIL);





        } catch (JSONException e) {
            e.printStackTrace();
        }


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
        editor.putString(config.USERNAME_SHARED_PREF, idUser);
        editor.putString("idUser", idUser);
        editor.putString("tel",phone);
        editor.putString("address",address);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("email", temail);
        editor.apply();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

        config.idUserUpdate = uID;
    }





}
