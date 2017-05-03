package com.example.mcnewz.icareservice.activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.fragment.HistorySendAlertFragment;
import com.example.mcnewz.icareservice.fragment.NewsShowDetailFragment;
import com.example.mcnewz.icareservice.fragment.NotificationBackFragment;

public class ShowDetailListViewActivity extends AppCompatActivity {

    ItemDao dao;
    private String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_list_view);

        dao = getIntent().getParcelableExtra("dao");
        tab = getIntent().getStringExtra("tab");

        setTitle(dao.getSubject());

        initInstance();


        // AlertTab Fragment here !
        if (savedInstanceState == null) {
            if(tab.equals("NEWS")){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainerShowDetailListView,
                                NewsShowDetailFragment.newInstance(dao),
                                "NewsShowDetailFragment")
                        .commit();
            }

        }

    }

    private void initInstance() {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }
}
