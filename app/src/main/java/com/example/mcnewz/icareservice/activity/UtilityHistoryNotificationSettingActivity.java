package com.example.mcnewz.icareservice.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.fragment.AlertTabFragment;
import com.example.mcnewz.icareservice.fragment.HistorySendAlertFragment;
import com.example.mcnewz.icareservice.fragment.NotificationBackFragment;
import com.example.mcnewz.icareservice.fragment.ProfileFragment;

public class UtilityHistoryNotificationSettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_history_notification_setting);
        tab = getIntent().getStringExtra("tab");

        initInstances();
        initToolbar();

        // AlertTab Fragment here !
        if (savedInstanceState== null) {

            if(tab.equals("notification")){

                setTitle("Notification Back");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerUtility,
                                NotificationBackFragment.newInstance("load"),
                                "NotificationBackFragment")
                        .commit();

            }else if(tab.equals("history")) {
                setTitle("History Send");

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerUtility,
                                HistorySendAlertFragment.newInstance(),
                                "HistorySendAlertFragment")
                        .commit();


            }else if(tab.equals("profile")){
                setTitle("Profile");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerUtility,
                            ProfileFragment.newInstance(),
                            "ProfileFragment")
                        .commit();
            }



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
                onBackPressed();

                return true;

        }
        return super.onOptionsItemSelected(item);

    }

}
