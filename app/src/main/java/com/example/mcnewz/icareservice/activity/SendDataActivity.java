package com.example.mcnewz.icareservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.fragment.RegencyInfomationFragment;
import com.example.mcnewz.icareservice.fragment.RegencyListFragment;
import com.example.mcnewz.icareservice.fragment.WarningInfomationFragment;

public class SendDataActivity extends AppCompatActivity implements
        RegencyListFragment.FragmentListener,
        RegencyInfomationFragment.FragmentListener,
        WarningInfomationFragment.FragmentListener{

    RegencyInfomationFragment infomationFragment;

    private String lat;
    private String lng;
    private String typeAc;
    private String typeName;


    private Toolbar toolbar;
    private String tab;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        initToolbar();

        idUser = getIntent().getStringExtra("idUser");
        tab = getIntent().getStringExtra("tab");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        typeAc = getIntent().getStringExtra("typeAc");
        typeName = getIntent().getStringExtra("typeName");


        // Fragment here !
        if (savedInstanceState== null) {
            if (tab.equals("a")){
                // send lat lng to RegencyListFragment for read near department
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerSendData, RegencyListFragment.newInstance(lat, lng))
                        .commit();
            }else{
                // WarningInfomationFragment to open
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerSendData, WarningInfomationFragment.newInstance(idUser, lat, lng))
                        .commit();
            }


            // test one two
            infomationFragment = RegencyInfomationFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerSendData, infomationFragment,
                            "RegencyInfomationFragment")
                    .detach(infomationFragment)
                    .commit();
        }
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
                Toast.makeText(this, "Finish Send Activity", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }

    // regencyInfomation to MainFragment
    @Override
    public void onSendClickRegencyInfo(String finishSendData, String tab) {

//        if(tab == "a"){
//
//        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        // finish Activity sendDataActivity
//        Intent intent = new Intent();
//        intent.putExtra("result",123456);
//        setResult(Activity.RESULT_OK,intent);
//        finish();

    }

    // regencyList
    @Override
    public void onSendClickRegencyListDepartment(String openFragment, String checkValue, String departSelect) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.contentContainerSendData);

        if(fragment instanceof RegencyInfomationFragment == false){
            getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.contentContainerSendData,  RegencyInfomationFragment.newInstance(idUser, lat, lng, typeAc,typeName, checkValue, departSelect))
                    .addToBackStack(null)
                    .commit();
        }
    }


}
