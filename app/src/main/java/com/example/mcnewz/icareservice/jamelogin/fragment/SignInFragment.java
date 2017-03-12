package com.example.mcnewz.icareservice.jamelogin.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.mcnewz.icareservice.jamelogin.manager.CheckNetwork;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SignInFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    private EditText edtEmailLogin;
    private EditText edtPasswordLogin;
    private Button btnSigninLogin;
    private boolean loggedIn = false;
    String uID;


    private static final String TAG = "FacebookLoginActivity";
    private CallbackManager mCallbackManager;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private LoginButton loginButton;
    private Button btnFacebook;



    private SignInButton signInButton;
    private static final String TAGG = "GoogleSignInActivity";
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private  Button btnGoogle;

//-----------------------------------------------------------------------------------------------

    private ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    //--------------------------------------------------------------------------------------------
    public SignInFragment() {
        super();
    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_in_sign_login, container, false);
        initInstances(rootView);

        return rootView;
    }
    private void initInstances(View rootView) {

        // init instance with rootView.findViewById here
        //setRetainInstance(true);
        edtEmailLogin = (EditText) rootView.findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = (EditText) rootView.findViewById(R.id.edtPasswordLogin);
        btnSigninLogin = (Button) rootView.findViewById(R.id.btnSigninLogin);
        btnSigninLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verify();

            }
        });
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) rootView.findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        // If using in a fragment

        // Other app specific specialization
        // Callback registration
        btnFacebook = (Button)rootView.findViewById(R.id.btnFacebook);
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

        btnGoogle = (Button)rootView.findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                    signIn();

                } else {
                    // No Internet
                    Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
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
        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                }
                hideProgressDialog();

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
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAGG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {

                } else {
                }
                hideProgressDialog();
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
                            updatetoken();

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }else{
                            getFragmentManager().beginTransaction()
                                    .setCustomAnimations(
                                            R.anim.from_right,R.anim.to_left,
                                            R.anim.from_left,R.anim.to_right

                                    )
                                    .replace(R.id.contentContainer, CreateAccountFragment.newInstance())
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
        hideProgressDialog();
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

    private  void  verify(){
        if(edtEmailLogin.getText().toString().trim().isEmpty()){
            edtEmailLogin.setError("Invalid Email");
            edtEmailLogin.requestFocus();
        }else if(!validateEmail(edtEmailLogin.getText().toString())){
            edtEmailLogin.setError("Invalid Email");
            edtEmailLogin.requestFocus();
        }else if(edtPasswordLogin.getText().toString().trim().isEmpty()){
            edtPasswordLogin.setError("Invalid Password");
            edtPasswordLogin.requestFocus();
        }else {
            login();
        }
    }


    private void login(){
        //Getting values from edit texts
        final String username = edtEmailLogin.getText().toString().trim();
        final String password = edtPasswordLogin.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.trim().equalsIgnoreCase(config.LOGIN_SUCCESS)){
                            //Creating a shared preference
//                            SharedPreferences sharedPreferences = SignInFragment.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //Adding values to editor
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.USERNAME_SHARED_PREF, username);
                            //Saving values to editor
                            editor.commit();
                            edtEmailLogin.setText("");
                            edtPasswordLogin.setText("");
                            uID = username;
                            updatetoken();
                            //Starting profile activity

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(getContext(), "Invalid username or password"+response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(config.LOGIN_USERNAME, username);
                params.put(config.LOGIN_PASSWORD, password);
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    //--------------------Check Email--end------------------------------------

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
}
