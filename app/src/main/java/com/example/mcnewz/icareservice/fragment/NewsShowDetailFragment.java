package com.example.mcnewz.icareservice.fragment;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.ItemDao;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NewsShowDetailFragment extends Fragment {

    private TextView tvHello;
    ItemDao dao;
    private CoordinatorLayout rootLayout;
    private FloatingActionButton fabBtn;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    Toolbar toolbar;
    private ImageView ivShowAcident;
    private ImageView ivTypeAcident;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvDetail;
    private TextView tvAddress;


    private Geocoder geocoder;
    private List<Address> address;

    public NewsShowDetailFragment() {
        super();
    }

    public static NewsShowDetailFragment newInstance(ItemDao dao) {
        NewsShowDetailFragment fragment = new NewsShowDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_show_detail, container, false);

        setHasOptionsMenu(true);

        dao = getArguments().getParcelable("dao");



        initInstances(rootView);
        initToolbar(rootView);


        return rootView;
    }

    private void initToolbar(View rootView) {
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);

        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initInstances(View rootView) {

        ivShowAcident = (ImageView) rootView.findViewById(R.id.ivShowAcident);
        ivTypeAcident = (ImageView) rootView.findViewById(R.id.ivTypeAcident);

        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvTime = (TextView) rootView.findViewById(R.id.tvTime);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);


        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.rootLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsingToolbarLayout);
        fabBtn = (FloatingActionButton) rootView.findViewById(R.id.fabBtn);


        setDataNewsItem();


    }

    private void setDataNewsItem() {

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().onBackPressed();

                            }
                        })
                        .show();
            }
        });

        collapsingToolbarLayout.setTitle(dao.getSubject());

        Glide.with(getContext())
                .load(dao.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivShowAcident);

        int type = dao.getType();
        if(type == 1){
            ivTypeAcident.setImageResource(R.drawable.a1);
        }
        if(type == 2){
            ivTypeAcident.setImageResource(R.drawable.a2);
        }

        if(type == 3){
            ivTypeAcident.setImageResource(R.drawable.a3);
        }

        if(type == 4){
            ivTypeAcident.setImageResource(R.drawable.a4);
        }

        Date dateCreate = dao.getCreate_date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(dateCreate);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            address = geocoder.getFromLocation(Double.parseDouble(dao.getLat()) ,Double.parseDouble(dao.getLng()), 1);
            if(address != null){
                String  adressme =  address.get(0).getAddressLine(0);
                String  city =  address.get(0).getLocality();
                String  state =  address.get(0).getAdminArea();
                String  country =  address.get(0).getCountryName();
                String  postalCode =  address.get(0).getPostalCode();
                String  knowName =  address.get(0).getFeatureName();


                tvAddress.setText(adressme +" "+ city +" "+ state +" "+ country+" "+ postalCode +" "+ knowName);
            }else{
                tvAddress.setText("จุดเกิดเหตุ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        tvDate.setText(dateString );
        tvTitle.setText(dao.getSubject());
        tvTime.setText(dao.getTime_submit());
        tvDetail.setText(dao.getDetail());



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

        super.onCreateOptionsMenu(menu,inflater);
    }



    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();

                return true;

        }
        return super.onOptionsItemSelected(item);



    }
}
