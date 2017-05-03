package com.example.mcnewz.icareservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.adapter.HistorySendAdapter;
import com.example.mcnewz.icareservice.adapter.NotificationBackAdapter;
import com.example.mcnewz.icareservice.dao.HistorySendItemCollectionDao;
import com.example.mcnewz.icareservice.dao.HistorySendItemDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemDao;
import com.example.mcnewz.icareservice.dao.SendIDUser;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.example.mcnewz.icareservice.manager.NotificationBackListManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HistorySendAlertFragment extends Fragment {

    private ListView listView;
    HistorySendAdapter listAdapter;


    private SendIDUser itemInfo;
    private String userId;

    public HistorySendAlertFragment() {
        super();
    }

    public static HistorySendAlertFragment newInstance() {
        HistorySendAlertFragment fragment = new HistorySendAlertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_send, container, false);


        SharedPreferences sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sp.getString(config.USERNAME_SHARED_PREF, "0");

        itemInfo = new SendIDUser();
        itemInfo.setUpIdUser(userId);

        initInstances(rootView);

        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here

        listView = (ListView) rootView.findViewById(R.id.lvHistory);

        setHistorySend();
    }

    private void setHistorySend() {

        listAdapter = new HistorySendAdapter();
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "Position" + position, Toast.LENGTH_SHORT).show();

            }
        });



        Call<HistorySendItemCollectionDao> call = HttpManager.getInstance().getService().setHistorySend(
                itemInfo.getUpIdUser()
        );

        call.enqueue(new Callback<HistorySendItemCollectionDao>() {
            @Override
            public void onResponse(Call<HistorySendItemCollectionDao> call, Response<HistorySendItemCollectionDao> response) {
                if(response.isSuccessful()){
                    HistorySendItemCollectionDao dao = response.body();
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();

                }else{

                }
            }

            @Override
            public void onFailure(Call<HistorySendItemCollectionDao> call, Throwable t) {

            }
        });
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
}
