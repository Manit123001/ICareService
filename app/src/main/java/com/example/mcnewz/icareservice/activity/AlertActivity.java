package com.example.mcnewz.icareservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.fragment.AlertFragment;
import com.example.mcnewz.icareservice.fragment.AlertTabFragment;
import com.example.mcnewz.icareservice.fragment.OneFragment;
import com.example.mcnewz.icareservice.fragment.TwoFragment;
import com.example.mcnewz.icareservice.fragment.WarningFragment;
import com.example.mcnewz.icareservice.jamelogin.manager.CheckNetwork;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;


public class AlertActivity extends AppCompatActivity implements
        AlertFragment.FragmentListener,
        WarningFragment.FragmentListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        initInstances();
        initToolbar();

        idUser = getIntent().getStringExtra("idUser");

        // AlertTab Fragment here !
        if (savedInstanceState== null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerAlert,
                            AlertTabFragment.newInstance(),
                            "AlertTabFragment")
                    .commit();
        }

    }

    private void initInstances() {

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(this, "Button Up", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }


    //alert Send ***
    @Override
    public void onSendClickAlertFrament(String tab, String lat, String lng, String typeAc, String typeName) {

        if(tab == "a"){
            // CheckInternet
            if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {

                Intent intent = new Intent(AlertActivity.this, SendDataActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("tab", tab);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("typeAc", typeAc);
                intent.putExtra("typeName", typeName);
                startActivity(intent);
            } else {
                // No Internet
                Toast.makeText(Contextor.getInstance().getContext(), "Please Connect Internet", Toast.LENGTH_SHORT).show();
            }


        }
    }

    //warning Send ***
    @Override
    public void onSendClickWarningFrament(String tab, String lat, String lng, String typeAc, String typeName) {
        if(tab == "w"){

            // CheckInternet
            if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
                // your get/post related code..like HttpPost = new HttpPost(url);

                Intent intent = new Intent(AlertActivity.this, SendDataActivity.class);
                intent.putExtra("tab", tab);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("typeAc", typeAc);
                intent.putExtra("typeName", typeName);
                startActivity(intent);
            } else {
                // No Internet
                Toast.makeText(Contextor.getInstance().getContext(), "Please Connect Internet", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
