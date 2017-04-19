package com.example.mcnewz.icareservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.fragment.AlertTabFragment;
import com.example.mcnewz.icareservice.fragment.NotificationBackFragment;

public class UtilityHistoryNotificationSettingActivity extends AppCompatActivity {

    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_history_notification_setting);
        idUser = getIntent().getStringExtra("idUser");

        // AlertTab Fragment here !
        if (savedInstanceState== null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainerUtility,
                            NotificationBackFragment.newInstance("load"),
                            "NotificationBackFragment")
                    .commit();
        }
    }
}
