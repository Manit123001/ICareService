package com.example.mcnewz.icareservice.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.NeaRegencyListItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NearRegencyListItemDao;
import com.example.mcnewz.icareservice.dao.SendDataList;
import com.example.mcnewz.icareservice.manager.CheckNetwork;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RegencyListFragment extends Fragment {


    private TextView tvNameList;
    private TextView tvTelList;
    private TextView tvDetailList;
    private CheckBox chkSelected;
    private CheckBox chkSelected2;
    private CheckBox chkSelected4;
    private CheckBox chkSelected3;

    private TextView tvName2List;
    private TextView tvTel2List;
    private TextView tvDetail2List;

    private TextView tvName3List;
    private TextView tvTel3List;
    private TextView tvDetail3List;

    private TextView tvName4List;
    private TextView tvTel4List;
    private TextView tvDetail4List;

    private ImageView ivDepartmentType4;
    private ImageView ivDepartmentType1;
    private ImageView ivDepartmentType3;
    private ImageView ivDepartmentType2;
    private String lat;
    private String lng;
    private ProgressDialog pDialog;

    private ImageButton ibCall4;
    private ImageButton ibCall3;
    private ImageButton ibCall2;
    private ImageButton ibCall1;

    SendDataList itemInfo;

    public RegencyListFragment() {
        super();
    }

    public static RegencyListFragment newInstance(String lat,
                                                  String lng) {
        RegencyListFragment fragment = new RegencyListFragment();
        Bundle args = new Bundle();


        args.putString("lat", lat);
        args.putString("lng", lng);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
//
        lat = getArguments().getString("lat");
        lng = getArguments().getString("lng");

        setRegencyInfo();
        setItemInfo();

    }




    private void setRegencyInfo() {
        showpDialog();
        itemInfo = new SendDataList();

        setItemInfo();

    }

    private void setItemInfo() {
        itemInfo.setDistance_lat(lat);
        itemInfo.setDistance_lng(lng);
//        Toast.makeText(getContext(), "444ละติจูด"+lat+"ลองติจูด"+ lng, Toast.LENGTH_SHORT).show();

    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_regency_list, container, false);
        initInstances(rootView);
        return rootView;
    }

    String checkValue= "No" ;

    private void initInstances(View rootView) {

        // init instance with rootView.findViewById here
        tvNameList = (TextView) rootView.findViewById(R.id.tvNameList);
        tvTelList = (TextView) rootView.findViewById(R.id.tvTelList);
        tvDetailList = (TextView) rootView.findViewById(R.id.tvDetailList);

        tvName2List = (TextView) rootView.findViewById(R.id.tvName2List);
        tvTel2List = (TextView) rootView.findViewById(R.id.tvTel2List);
        tvDetail2List = (TextView) rootView.findViewById(R.id.tvDetail2List);

        tvName3List = (TextView) rootView.findViewById(R.id.tvName3List);
        tvTel3List = (TextView) rootView.findViewById(R.id.tvTel3List);
        tvDetail3List = (TextView) rootView.findViewById(R.id.tvDetail3List);

        tvName4List = (TextView) rootView.findViewById(R.id.tvName4List);
        tvTel4List = (TextView) rootView.findViewById(R.id.tvTel4List);
        tvDetail4List = (TextView) rootView.findViewById(R.id.tvDetail4List);

        chkSelected = (CheckBox) rootView.findViewById(R.id.chkSelected);
        chkSelected2 = (CheckBox) rootView.findViewById(R.id.chkSelected2);
        chkSelected3 = (CheckBox) rootView.findViewById(R.id.chkSelected3);
        chkSelected4 = (CheckBox) rootView.findViewById(R.id.chkSelected4);

        ivDepartmentType1 = (ImageView) rootView.findViewById(R.id.ivDepartmentType1);
        ivDepartmentType2 = (ImageView) rootView.findViewById(R.id.ivDepartmentType2);
        ivDepartmentType3 = (ImageView) rootView.findViewById(R.id.ivDepartmentType3);
        ivDepartmentType4 = (ImageView) rootView.findViewById(R.id.ivDepartmentType4);

        ibCall1 = (ImageButton) rootView.findViewById(R.id.ibCall);
        ibCall2 = (ImageButton) rootView.findViewById(R.id.ibCall2);
        ibCall3 = (ImageButton) rootView.findViewById(R.id.ibCall3);
        ibCall4 = (ImageButton) rootView.findViewById(R.id.ibCall4);

        if (new CheckNetwork(Contextor.getInstance().getContext()).isNetworkAvailable()) {
            // your get/post related code..like HttpPost = new HttpPost(url);
            callBackItemDepartments();

        } else {
            // No Internet
            Toast.makeText(Contextor.getInstance().getContext(), "Please Connect Internet", Toast.LENGTH_SHORT).show();
        }

    }

    String idList1;
    String idList2;
    String idList3;
    String idList4;

    // Show Data
    private void callBackItemDepartments() {
        Call<NeaRegencyListItemCollectionDao> call = HttpManager.getInstance().getService().loadItemListNearDepartment(
                itemInfo.getDistance_lat(),
                itemInfo.getDistance_lng()
        );
        call.enqueue(new Callback<NeaRegencyListItemCollectionDao>() {
            @Override
            public void onResponse(Call<NeaRegencyListItemCollectionDao> call, Response<NeaRegencyListItemCollectionDao> response) {
                hidepDialog();
                NearRegencyListItemDao dao ;
                NeaRegencyListItemCollectionDao collectionDao = response.body();
                int sizeDao = collectionDao.getData().size();

                for (int i = 0; i < sizeDao; i++ ){

                    dao = collectionDao.getData().get(i);

                    int id = dao.getId();
                    String subjectList = dao.getName();
                    String detailList = dao.getDescription();
                    final String telList = dao.getTel();
                    int type = dao.getTypeId();

                    int typeDe;
                    if(type == 1){
                        typeDe = R.drawable.ic_depart_police;


                    } else if (type == 2){
                        typeDe = R.drawable.ic_depart_hospital;


                    }else if (type == 3){
                        typeDe = R.drawable.ic_depart_fire;


                    }else {
                        typeDe = R.drawable.ic_depart_wor;

                    }
                    if(type == 1){
                        tvNameList.setText(subjectList);
                        tvTelList.setText(telList);
                        tvDetailList.setText(detailList);
                        ivDepartmentType1.setImageResource(typeDe);

                        ibCall1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "cal 1", Toast.LENGTH_SHORT).show();

                                actionCall(telList);
                            }
                        });
                        idList1= String.valueOf(id);
                        checkVlue1 = subjectList;
                    }

                    if (type == 2){
                        tvName2List.setText(subjectList);
                        tvTel2List.setText(telList);
                        tvDetail2List.setText(detailList);
                        ivDepartmentType2.setImageResource(typeDe);
                        ibCall2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "cal 2", Toast.LENGTH_SHORT).show();
                                actionCall(telList);

                            }
                        });
                        idList2= String.valueOf(id);
                        checkVlue2 = subjectList;
                    }

                    if(type == 3){
                        tvName3List.setText(subjectList);
                        tvTel3List.setText(telList);
                        tvDetail3List.setText(detailList);
                        ivDepartmentType3.setImageResource(typeDe);
                        ibCall3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "cal 3", Toast.LENGTH_SHORT).show();
                                actionCall(telList);

                            }
                        });
                        idList3 = String.valueOf(id);
                        checkVlue3 = subjectList;
                    }

                    if (type == 4){
                        tvName4List.setText(subjectList);
                        tvTel4List.setText(telList);
                        tvDetail4List.setText(detailList);
                        ivDepartmentType4.setImageResource(typeDe);

                        ibCall4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "cal 4", Toast.LENGTH_SHORT).show();
                                actionCall(telList);

                            }
                        });
                        idList4= String.valueOf(id);
                        checkVlue4 = subjectList;
                    }
                }
            }

            @Override
            public void onFailure(Call<NeaRegencyListItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), "XXXXX"+t.toString()+"DepartmentsItemCollectionDao Erorr", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void actionCall(String callUrl) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callUrl.trim()));
        startActivity(intent);


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
        inflater.inflate(R.menu.menu_send2, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public interface FragmentListener{
        void onSendClickRegencyListDepartment(String tab,
                                              String checkValue,
                                              String departSelect);
    }

    String c1,c2,c3,c4,checkValueSelect;
    String checkVlue1;
    String checkVlue2;
    String checkVlue3;
    String checkVlue4;

    String cvSelect1;
    String cvSelect2;
    String cvSelect3;
    String cvSelect4;
    String departSelect;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_send:

                if (chkSelected.isChecked()) {
                    checkValue = "ตำรวจ";
                    c1 = idList1;
                    cvSelect1 = checkVlue1;

                }else{
                    c1 = "";
                    cvSelect1="";
                }
                if(chkSelected2.isChecked()){
                    checkValue = "โรงพยาบาล";
                    if(c1 == ""){
                        c2 = idList2;
                        cvSelect2 = checkVlue2;


                    }else{
                        c2 = ","+idList2;
                        cvSelect2 = ","+checkVlue2;

                    }
                }else{
                    c2 = "";
                    cvSelect2 = "";
                }

                if(chkSelected3.isChecked()){
                    checkValue = "กู้ภัย";
                    if(c1 == "" && c2 == ""){
                        c3 = idList3;
                        cvSelect3 = checkVlue3;

                    }else{
                        c3 = ","+idList3;
                        cvSelect3 = ","+checkVlue3;

                    }

                }else {
                    c3 = "";
                    cvSelect3 = "";
                }
                if(chkSelected4.isChecked()){
                    checkValue = "ดับเพลิง";
                    if(c1 == "" && c2 == "" && c3 == ""){
                        c4 = idList4;
                        cvSelect4 = checkVlue4;

                    }else{
                        c4 = ","+idList4;
                        cvSelect4 = ","+checkVlue4;
                    }

                }else{
                    c4 = "";
                    cvSelect4 = "";
                }



                checkValueSelect = c1+c2+c3+c4;
                departSelect = cvSelect1+cvSelect2+cvSelect3+cvSelect4;

                RegencyListFragment.FragmentListener listener =  (RegencyListFragment.FragmentListener) getActivity();
                listener.onSendClickRegencyListDepartment(
                        "1",
                        checkValueSelect.trim(),
                        departSelect.trim());
                break;

        }
        return true;
    }
}
