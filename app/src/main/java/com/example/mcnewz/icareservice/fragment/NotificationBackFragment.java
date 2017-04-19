package com.example.mcnewz.icareservice.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.activity.MainActivity;
import com.example.mcnewz.icareservice.adapter.NewsAcidentsAdapter;
import com.example.mcnewz.icareservice.adapter.NotificationBackAdapter;
import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.dao.NeaRegencyListItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NearRegencyListItemDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemDao;
import com.example.mcnewz.icareservice.dao.SendDataList;
import com.example.mcnewz.icareservice.dao.SendIDUser;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.example.mcnewz.icareservice.manager.NewsAcidentsListManager;
import com.example.mcnewz.icareservice.manager.NotificationBackListManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NotificationBackFragment extends Fragment {

    private Toolbar toolbar;

    private String idUser;

    // Button Sheet

//    private ListView listView;
//    private NewsAcidentsAdapter listAdapter;
//    NewsAcidentsListManager newsAcidentsListManager;

    private ListView listView;
    private NotificationBackAdapter listAdapter;
    private NotificationBackListManager notificationBackListManager;
    private SendIDUser  itemInfo;
    private ProgressDialog pDialog;
    private String load ="";
    private String userId;

    public NotificationBackFragment() {
        super();
    }

    public static NotificationBackFragment newInstance() {
        NotificationBackFragment fragment = new NotificationBackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static NotificationBackFragment newInstance(String load) {
        NotificationBackFragment fragment = new NotificationBackFragment();
        Bundle args = new Bundle();

        args.putString("load",load);

        fragment.setArguments(args);


        return fragment;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        load = getArguments().getString("load");

        SharedPreferences sp = getActivity().getSharedPreferences("Main_Fragment", Context.MODE_PRIVATE);
        userId = sp.getString("idUser", "0");

        setNotificationInfo(load);
    }

    private void setNotificationInfo(String load) {
        if(load != null){
            showpDialog();
        }
        itemInfo = new SendIDUser();
        setItemInfo();

    }

    private void setItemInfo() {
        itemInfo.setUpIdUser(userId);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_back, container, false);

        final Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        listView = (ListView) rootView.findViewById(R.id.lvNotification);

        setNewsAcidentsShow();
    }

    private void setNewsAcidentsShow(){
        listAdapter = new NotificationBackAdapter();
        listView.setAdapter(listAdapter);
        notificationBackListManager = new NotificationBackListManager();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotificationBackItemDao dao = notificationBackListManager.getDao().getData().get(position);
                //FragmentListener listener =  (FragmentListener) getActivity();
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                //listener.onPhotoItemClicked(dao);
            }
        });

        Call<NotificationBackItemCollectionDao> call = HttpManager.getInstance().getService().setNotificationBack(
                itemInfo.getUpIdUser()
        );

        call.enqueue(new Callback<NotificationBackItemCollectionDao>() {
            @Override
            public void onResponse(Call<NotificationBackItemCollectionDao> call, Response<NotificationBackItemCollectionDao> response) {
                hidepDialog();

                if ( response.isSuccessful()){
                    NotificationBackItemCollectionDao dao = response.body();
                    //Toast.makeText(Contextor.getInstance().getContext(), "Complete"+dao.getData().get(0).getId(), Toast.LENGTH_SHORT).show();
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();

                    notificationBackListManager.setDao(dao);
                }else{
                    hidepDialog();
                    try {
                        Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationBackItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void showpDialog() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_send, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
//                getFragmentManager().popBackStack();
                getActivity().onBackPressed();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
