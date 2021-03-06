package com.example.mcnewz.icareservice.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.fragment.HistorySendAlertFragment;
import com.example.mcnewz.icareservice.fragment.MainFragment;
import com.example.mcnewz.icareservice.fragment.NotificationBackFragment;
import com.example.mcnewz.icareservice.fragment.ProfileFragment;


public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private NavigationView navigationView;
    private TextView tvName;
    private TextView tvMail;

    private String firstname="";
    private String lastname ="";
    private String email = "";

    private ProgressDialog loading;
    String user_id = "";
    private NotificationBackFragment notificationBackFragment;
    private HistorySendAlertFragment historySendAlertFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment here !
        if (savedInstanceState == null) {
            //initInstances();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();

            // test one two
            notificationBackFragment = NotificationBackFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, notificationBackFragment,
                            "RegencyInfomationFragment")
                    .detach(notificationBackFragment)
                    .commit();

            historySendAlertFragment = HistorySendAlertFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, historySendAlertFragment,
                            "RegencyInfomationFragment")
                    .detach(historySendAlertFragment)
                    .commit();
        }


        initToolbar();
//        initInstances();

    }
//    private void initInstances() {
////        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
//  //      tvName = (TextView)headerLayout.findViewById(R.id.tvName);
////        tvMail = (TextView)headerLayout.findViewById(R.id.tvMail);

//    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    // Toolbar here
    private void initToolbar() {
        // setActionbar Toolbar

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // drawer layout Here
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(
//                MainActivity.this,
//                drawerLayout,
//                R.string.open_drawer,
//                R.string.close_drawer
//        );
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);


    }
    boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onNewsBlogItemClicked(ItemDao dao, String tab) {

        Intent intent = new Intent(MainActivity.this, ShowDetailListViewActivity.class);

        intent.putExtra("dao", dao);
        intent.putExtra("tab", tab);
        startActivity(intent);
    }

    @Override
    public void onDrawableMenuClickList(String tabClick, String idUser) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.contentContainer);

        if(tabClick == "m"){
            if(!(fragment instanceof MainFragment)){
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.contentContainer,  MainFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        }else if (tabClick == "profile"){

            Intent intent = new Intent(MainActivity.this, UtilityHistoryNotificationSettingActivity.class);
            intent.putExtra("tab", "profile");
            startActivity(intent);
        } else if (tabClick == "h"){

            Intent intent = new Intent(MainActivity.this, UtilityHistoryNotificationSettingActivity.class);
            intent.putExtra("tab", "history");
            startActivity(intent);

        }else if (tabClick == "n"){

            Intent intent = new Intent(MainActivity.this, UtilityHistoryNotificationSettingActivity.class);
            intent.putExtra("tab", "notification");
            startActivity(intent);
        }

    }
}
