package com.example.mcnewz.icareservice.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.activity.AlertActivity;
import com.example.mcnewz.icareservice.adapter.NewsAcidentsAdapter;
import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.dao.WarningItemCollectionDao;
import com.example.mcnewz.icareservice.dao.WarningItemDao;
import com.example.mcnewz.icareservice.jamelogin.activity.MainLoginActivity;
import com.example.mcnewz.icareservice.manager.CheckNetwork;
import com.example.mcnewz.icareservice.jamelogin.manager.config;
import com.example.mcnewz.icareservice.manager.NewsAcidentsListManager;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */

public class MainFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener
{
    /************
     * Variables
     *************/
    private final String LOG = "LaurenceTestApp";

    // Location
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    LatLng latLng;

    // Marker
    Marker mCurrLocationMarker;
    Marker marker1;
    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray2 = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray3 = new ArrayList<Marker>();
    private ArrayList<Marker> mMarkerArray4 = new ArrayList<Marker>();
    private Marker markerSearchLocation;

    // View XML
    private Button btnOut;
    private Button btnIn;
    FloatingActionButton fabBtnSendLocation;
    FloatingActionButton fabLocation;
    FloatingSearchView mSearchView;
    ImageButton btnShowMark1;
    ImageButton btnShowMark2;
    ImageButton btnShowMark3;
    ImageButton btnShowMark4;
    private TextView tvSlide;

    // googleMap
    boolean mapReady = false;
    MapView mMapView;
    private GoogleMap mMap;

    // Button Sheet
    View parentView;
    AcidentsBottomSheetDialog bottomSheetDialog;
    DepartmentsBottomSheetDialog departmentsBottomSheetDialog;
    View bottomSheetNewsAcidents;
    private BottomSheetBehavior<View> behavior;
    private ListView listView;
    private NewsAcidentsAdapter listAdapter;
    NewsAcidentsListManager newsAcidentsListManager;

    // Index Location
    ArrayList<Integer> localClick = new ArrayList<Integer>();
    ArrayList<Integer> localClickDepartment = new ArrayList<Integer>();

    //Mode check
    public static final int MODE_ACIDENT = 1;
    public static final int MODE_DEPARTMENT = 2;


    // varia ble
    int type1 = 1;
    int type2 = 1;
    int type3 = 1;
    int type4 = 1;

    // Drawable
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private View headerLayout;
    private TextView tvName;


    private TextView tvMail;
    String firstname,lastname,email,user_id;
    private ProgressDialog loading;
    private String idUser;


    private com.google.firebase.auth.FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private FragmentListener listener;


    /************
     * Functions
     *************/

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mGoogleApiClient == null){
            buildGoogleApiClient();

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        parentView = inflater.inflate(R.layout.layout_custom_bottom_sheet,null);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        initInstances(rootView); // init here

        return rootView;
    }

    // Button All Find Here
    private void initInstances(final View rootView) {

        navigationView = (NavigationView) rootView.findViewById(R.id.navigation);
        mSearchView = (FloatingSearchView)rootView.findViewById(R.id.floating_search_view);
        fabLocation = (FloatingActionButton) rootView.findViewById(R.id.fabLocation);
        fabBtnSendLocation = (FloatingActionButton) rootView.findViewById(R.id.fabBtn);
        btnShowMark1 = (ImageButton) rootView.findViewById(R.id.btnShowMark1);
        btnShowMark2 = (ImageButton) rootView.findViewById(R.id.btnShowMark2);
        btnShowMark3 = (ImageButton) rootView.findViewById(R.id.btnShowMark3);
        btnShowMark4 = (ImageButton) rootView.findViewById(R.id.btnShowMark4);
        tvSlide = (TextView) rootView.findViewById(R.id.tvSlide);

        // listView
        listView = (ListView) rootView.findViewById(R.id.listView);

        //newsAcidents
        bottomSheetNewsAcidents = rootView.findViewById(R.id.bottomSheetNewsAcidents);
        behavior = BottomSheetBehavior.from(bottomSheetNewsAcidents);


        // drawer layout Here
        drawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawerLayoutFragmentMain);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        setListenerAllView();
        navigationViewLeftMenu();
        checkLocaationEnable();



        // CheckInternet
        if (new CheckNetwork(getContext()).isNetworkAvailable()) {
            LoginMenu();

            callBackItem(); // call back data
            //callWarningBackItem();
        } else {
            LoginMenu();
            // No Internet
            Toast.makeText(Contextor.getInstance().getContext(), "Please Connect Internet.", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkLocaationEnable() {
        LocationManager lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage(getContext().getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getContext().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getContext().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getContext().getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }


    private void setListenerAllView() {
        mSearchView.setOnSearchListener(floatSearchListener);
        mSearchView.setOnMenuItemClickListener(onMenuItemClickListener);
        mSearchView.attachNavigationDrawerToMenuButton(drawerLayout);

        fabLocation.setOnClickListener(fabLocationListener);
        fabBtnSendLocation.setOnClickListener(fabBtnSendLocationListener);

        btnShowMark1.setOnClickListener(showMarker1Listener);
        btnShowMark2.setOnClickListener(showMarker2Listener);
        btnShowMark3.setOnClickListener(showMarker3Listener);
        btnShowMark4.setOnClickListener(showMarker4Listener);

        behavior.setBottomSheetCallback(bottomSheetBehaviorNewsAcidents);
    }

    private void callBackItem() {
        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
        call.enqueue(new Callback<ItemCollectionDao>() {
            @Override
            public void onResponse(Call<ItemCollectionDao> call, Response<ItemCollectionDao> response) {
                if(response.isSuccessful()){
                    String detailDao,subject;
                    ItemDao dao;
                    ItemCollectionDao collectionDao = response.body();

                    int sizeDao = collectionDao.getData().size();
                    //Toast.makeText(Contextor.getInstance().getContext(), sizeDao+dao.getData().get(0).getLat(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < sizeDao; i++){
                        dao = collectionDao.getData().get(i);

                        int id = dao.getId();
                        String latDao = dao.getLat();
                        String lngDao = dao.getLng();
                        detailDao = dao.getDetail();
                        subject = dao.getSubject();
                        int type = dao.getType();

                        int typeAc = 0;
                        latLng = new LatLng(Double.parseDouble(latDao), Double.parseDouble(lngDao));

                        // type Marker
                        if(type == 1){
                            typeAc = R.drawable.a1;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray.add(marker1);

                        } else if (type == 2){
                            typeAc = R.drawable.a2;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray2.add(marker1);

                        }else if (type == 3){
                            typeAc = R.drawable.a3;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray3.add(marker1);

                        }else {
                            typeAc = R.drawable.a4;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray4.add(marker1);
                        }

                        localClick.add(id);
                        // show marker
                    }

                } else {
//                    try {
//                        Toast.makeText(Contextor.getInstance().getContext(),
//                                response.errorBody().string(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    Toast.makeText(Contextor.getInstance().getContext(),
                            "ตรวจสอบอินเตอร์เน็ต", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
//                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error", Toast.LENGTH_LONG).show();
                Toast.makeText(Contextor.getInstance().getContext(), "ตรวจสอบอินเตอร์เน็ต", Toast.LENGTH_LONG).show();
            }
        });
    }


    // TODO Warnning Feed
    private void callWarningBackItem() {
        Call<WarningItemCollectionDao> call = HttpManager.getInstance().getService().loadWarningItemList();
        call.enqueue(new Callback<WarningItemCollectionDao>() {
            @Override
            public void onResponse(Call<WarningItemCollectionDao> call, Response<WarningItemCollectionDao> response) {
                if(response.isSuccessful()){
                    String detailDao,subject;
                    WarningItemDao dao;
                    WarningItemCollectionDao collectionDao = response.body();

                    int sizeDao = collectionDao.getData().size();
                    //Toast.makeText(Contextor.getInstance().getContext(), sizeDao+dao.getData().get(0).getLat(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < sizeDao; i++){
                        dao = collectionDao.getData().get(i);

                        int id = dao.getId();
                        String latDao = dao.getLat();
                        String lngDao = dao.getLng();
                        detailDao = dao.getDetail();
                        subject = dao.getSubject();
                        int type = dao.getType();

                        int typeAc = 0;
                        latLng = new LatLng(Double.parseDouble(latDao), Double.parseDouble(lngDao));

                        // type Marker
                        if(type == 1){
                            typeAc = R.drawable.a1;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray.add(marker1);

                        } else if (type == 2){
                            typeAc = R.drawable.a2;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray2.add(marker1);

                        }else if (type == 3){
                            typeAc = R.drawable.a3;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray3.add(marker1);

                        }else {
                            typeAc = R.drawable.a4;
                            marker1 = getMarker(detailDao, subject, typeAc, latLng);
                            marker1.setTag(id);
                            mMarkerArray4.add(marker1);
                        }

                        localClick.add(id);
                        // show marker
                    }

//                    marker1 = mMap.addMarker(new MarkerOptions()
//                            .position(BRISBANE)
//                            .title("Brisbane")
//                            .snippet("Marker Description")
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
//                    marker1.setTag(100);
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(), Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WarningItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error 555", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void setNewsAcidentsShow(){
        listAdapter = new NewsAcidentsAdapter();
        listView.setAdapter(listAdapter);
        newsAcidentsListManager = new NewsAcidentsListManager();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDao dao = newsAcidentsListManager.getDao().getData().get(position);
                //FragmentListener listener =  (FragmentListener) getActivity();
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                //listener.onPhotoItemClicked(dao);
            }
        });

        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
        call.enqueue(new Callback<ItemCollectionDao>() {
            @Override
            public void onResponse(Call<ItemCollectionDao> call, Response<ItemCollectionDao> response) {
                if ( response.isSuccessful()){
                    ItemCollectionDao dao = response.body();

                    //Toast.makeText(Contextor.getInstance().getContext(), "Complete"+dao.getData().get(0).getId(), Toast.LENGTH_SHORT).show();
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();

                    newsAcidentsListManager.setDao(dao);
                }else{
                    try {
                        Toast.makeText(getContext(), response.errorBody().string()+ "success fail", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Marker getMarker(String snippet, String title, int typeAc, LatLng latLng) {
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(typeAc))
                .snippet(snippet)
                .title(title));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;

        // For showing a move to my location button
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
            }
        } else {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            }
            mMap.setMyLocationEnabled(true);

        }
        // Marker Click Here
        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        int sizeInArray = localClick.size();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            int indexArray = localClick.indexOf(clickCount);
            showBottomSheetDialog(clickCount , indexArray);
            //showToast("//Click Tag "+ clickCount + "//IndexArray = " + indexArray+ "//size" + sizeInArray+"//position" + marker.getPosition() );
        } else {
            Toast.makeText(Contextor.getInstance().getContext(), "I'm Here!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void showBottomSheetDialog(int clickCount ,int indexArray) {
        bottomSheetDialog = AcidentsBottomSheetDialog.newInstance(clickCount, indexArray);
        bottomSheetDialog.show(getFragmentManager(), "bottomsheet");
    }


    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    // LocationListener Here
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        updateLocation();
    }

    private void updateLocation() {
        if (mGoogleApiClient == null){
            buildGoogleApiClient();
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG, "Coinnect suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(LOG, "Coinnect failed"+ result.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG, location.toString());
        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Toast.makeText(Contextor.getInstance().getContext(), ""+String.valueOf(mLastLocation.getLatitude())+String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();

        //Place current location marker
        latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions options = new MarkerOptions()
                .title("Current Position")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                .position(latLng)
                .snippet("I am Here");

        mCurrLocationMarker = mMap.addMarker(options);
        // mCurrLocationMarker.setTag(1);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    // Facebook Code Login Jame
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // End Facebook

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

    // Change Type map
    public void changeType() {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void showToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    // Check Permission
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *1hronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        updateLocation();

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    // Start login of jame And Navigation View
    private void LoginMenu() {

        mAuth  = com.google.firebase.auth.FirebaseAuth.getInstance();
        mAuthListener = new com.google.firebase.auth.FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull com.google.firebase.auth.FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    config.status = 2;
                }
            }
        };

        // Login Page
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        user_id = sp.getString(config.USERNAME_SHARED_PREF,"");
        //Initializing textview
        if(config.status == 1){
            user_id = sp.getString(config.USERNAME_SHARED_PREF,"");
        }else {
            if (user != null) {
                user_id = user.getUid();
            }
        }

        // CheckInternet
        if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
            // your get/post related code..like HttpPost = new HttpPost(url);

                getData();

        } else {
            // No Internet
            Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
        }

        // init instance with rootView.findViewById here
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        tvMail = (TextView) headerLayout.findViewById(R.id.tvMail);

    }

    private void getData() {

        //loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,config.URL_DATA, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                showJSON(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(config.USERNAME_SHARED, user_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);

            idUser = collegeData.getString("member_id");

            firstname = collegeData.getString(config.READ_FIRSTNAME);
            lastname = collegeData.getString(config.READ_LASTNAME);
            email   = collegeData.getString(config.READ_EMAIL);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //     tvName.setText(firstname +" "+ lastname);
//        tvMail.setText(email);

        tvName.setText(firstname+" "+lastname);
        tvMail.setText(email);

    }

    public interface FragmentListener{
        void onDrawableMenuClickList(String tabClick, String idUser);
    }

    private void navigationViewLeftMenu() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                listener =  (FragmentListener) getActivity();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    case R.id.navItem1:
                        drawerLayout.closeDrawers();
                        listener.onDrawableMenuClickList("m", idUser);
                        return true;
                    case R.id.navItem2:
                        drawerLayout.closeDrawers();

                        listener.onDrawableMenuClickList("h", idUser);
                        return true;
                    case R.id.navItem3:
                        drawerLayout.closeDrawers();
                        listener.onDrawableMenuClickList("n", idUser);
                        return true;
                    case R.id.navItem4:
                        drawerLayout.closeDrawers();
                        listener.onDrawableMenuClickList("s", idUser);
                        return true;
                    case R.id.navItem5:
                        Toast.makeText(getContext(),"Logout",Toast.LENGTH_SHORT).show();
                        logout();
                        return true;

                    default:
                        Toast.makeText(getContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }

    private void logout() {

        if(config.status == 1){
            //Getting out sharedpreferences
            SharedPreferences preferences = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            //Getting editor
            SharedPreferences.Editor editor = preferences.edit();
            //Puting the value false for loggedin
            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);
            //Putting blank value to email
            editor.putString(config.USERNAME_SHARED_PREF, "");
            //Saving the sharedpreferences
            editor.apply();
            //Starting login activity

        }else {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

        }
        config.status = 1;
        Intent intent = new Intent(getContext(), MainLoginActivity.class);
        getActivity().finish();
        startActivity(intent);

    }
    // End Login For Jame


    /*******************
     * Listenner Zone
     *******************/
    View.OnClickListener fabBtnSendLocationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(), "HelloFloatMap", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), AlertActivity.class);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
        }
    };

    View.OnClickListener fabLocationListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            }
            checkLocaationEnable();
            updateLocation();

        }
    };

    int MODELOADSLIDENEWS = 1;
    BottomSheetBehavior.BottomSheetCallback bottomSheetBehaviorNewsAcidents = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            // React to state change
            //Toast.makeText(getContext(), "Start", Toast.LENGTH_SHORT).show();
            if (newState == 4 ){
                tvSlide.setText("Slide Up News" );
            } else{
                tvSlide.setText("เหตุร้ายรอบตัว");

            }

            if(MODELOADSLIDENEWS == 1){
                // Check internet
                if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {

                    setNewsAcidentsShow();
                    MODELOADSLIDENEWS = 0;
                } else {
                    // No Internet
                    Toast.makeText(Contextor.getInstance().getContext(), "no internet!", Toast.LENGTH_SHORT).show();
                }


            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            // React to dragging events
            //tvSlide.setText("......."+slideOffset);
            bottomSheet.showContextMenu();
            if(slideOffset == 0){
                MODELOADSLIDENEWS= 1;
            }

        }
    };


    FloatingSearchView.OnSearchListener floatSearchListener = new FloatingSearchView.OnSearchListener() {
        Address address;

        @Override
        public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            Toast.makeText(Contextor.getInstance().getContext(), "Click1", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSearchAction(String currentQuery) {
            try {

//                Toast.makeText(Contextor.getInstance().getContext(), "Here ... " + xx, Toast.LENGTH_SHORT).show();

                if(currentQuery != null ){

                    String location = currentQuery;
                    List<Address> addressList = null;

                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList != null){
                        address = addressList.get(0);
                    }
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    markerSearchLocation = mMap.addMarker(new MarkerOptions()
                            .position(latLng).title(location)
                            .snippet(address.getCountryName() + "\n" + address.getFeatureName()));
                    markerSearchLocation.setTag(0);

                    // move animateion
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                } else {

                }

                mSearchView.setActivated(false);

            }catch (IndexOutOfBoundsException e){
                Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();

            }
        }
    };

    FloatingSearchView.OnMenuItemClickListener onMenuItemClickListener = new FloatingSearchView.OnMenuItemClickListener() {
        @Override
        public void onActionMenuItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.typeMap:
                    showToast("Change Map");
                    changeType();
                    break;
                case R.id.action_settings:
                    showToast("Settings");
                    break;
            }
        }

    };


    View.OnClickListener showMarker1Listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (type1 == 1) {
                //Toast.makeText(getContext(), "x = 1", Toast.LENGTH_SHORT).show();
                btnShowMark1.setBackgroundResource(R.color.gray_active_icon);

                for (Marker marker : mMarkerArray) {
                    marker.setVisible(false);
                    //marker.remove(); <-- works too!
                }
                type1 = 0;

            } else {

                //Toast.makeText(getContext(), "x = 0", Toast.LENGTH_SHORT).show();
                btnShowMark1.setBackgroundResource(R.color.bottom_item_type_1);


                for (Marker marker : mMarkerArray) {
                    marker.setVisible(true);
                    //marker.remove(); <-- works too!


                }
                type1 = 1;

            }
        }
    };

    View.OnClickListener showMarker2Listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (type2 == 1) {
                //Toast.makeText(getContext(), "x = 1", Toast.LENGTH_SHORT).show();
                btnShowMark2.setBackgroundResource(R.color.gray_active_icon);

                for (Marker marker : mMarkerArray2) {
                    marker.setVisible(false);
                    //marker.remove(); <-- works too!
                }
                type2 = 0;

            } else {

                //Toast.makeText(getContext(), "x = 0", Toast.LENGTH_SHORT).show();
                btnShowMark2.setBackgroundResource(R.color.bottom_item_type_2);

                for (Marker marker : mMarkerArray2) {
                    marker.setVisible(true);
                    //marker.remove(); <-- works too!


                }
                type2 = 1;
            }
        }
    };

    View.OnClickListener showMarker3Listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (type3 == 1) {
                //Toast.makeText(getContext(), "x = 1", Toast.LENGTH_SHORT).show();
                btnShowMark3.setBackgroundResource(R.color.gray_active_icon);

                for (Marker marker : mMarkerArray3) {
                    marker.setVisible(false);
                    //marker.remove(); <-- works too!
                }
                type3 = 0;
            } else {

                //Toast.makeText(getContext(), "x = 0", Toast.LENGTH_SHORT).show();
                btnShowMark3.setBackgroundResource(R.color.bottom_item_type_3);

                for (Marker marker : mMarkerArray3) {
                    marker.setVisible(true);
                    //marker.remove(); <-- works too!

                }
                type3 = 1;
            }
        }
    };


    View.OnClickListener showMarker4Listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (type4 == 1) {
                //Toast.makeText(getContext(), "x = 1", Toast.LENGTH_SHORT).show();
                btnShowMark4.setBackgroundResource(R.color.gray_active_icon);

                for (Marker marker : mMarkerArray4) {
                    marker.setVisible(false);
                    //marker.remove(); <-- works too!
                }
                type4 = 0;

            } else {

                //Toast.makeText(getContext(), "x = 0", Toast.LENGTH_SHORT).show();
                btnShowMark4.setBackgroundResource(R.color.bottom_item_type_4);

                for (Marker marker : mMarkerArray4) {
                    marker.setVisible(true);
                    //marker.remove(); <-- works too!



                }
                type4 = 1;
            }
        }
    };

}
