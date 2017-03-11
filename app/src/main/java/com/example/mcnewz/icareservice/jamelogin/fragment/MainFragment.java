package com.example.mcnewz.icareservice.jamelogin.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.jamelogin.manager.ImageAdapter;


/**
 * Created by JAME on 18-Jan-17.
 */

public class MainFragment extends Fragment {
    //Image Slied
    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;
    Gallery gallery;
    //initially it is false
    private boolean loggedIn = false;
    private Button btnLogin,btnRegister;

    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.firebase.auth.FirebaseAuth mAuth;


//    int someVar;

//    public static MainFragment newInstance(String firstname,String lastname,String email){
//        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle(); // Arguments
//        //args.putInt("someVar", someVar);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //Read from argement
//        someVar = getArguments().getInt("someVar");


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_login,
                container,false);
        initInstansces(rootView);

        return rootView;

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    private void initInstansces(View rootView) {

        btnLogin = (Button)rootView.findViewById(R.id.btnLogIn);
        btnRegister = (Button)rootView.findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right,R.anim.to_left,
                                R.anim.from_left,R.anim.to_right

                        )
                        .replace(R.id.contentContainer, SignInFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right,R.anim.to_left,
                                R.anim.from_left,R.anim.to_right
                        )
                        .replace(R.id.contentContainer, RegisterFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    //-------------------------------------------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------------------
}
