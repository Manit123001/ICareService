package com.example.mcnewz.icareservice.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mcnewz.icareservice.R;
import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mayowa.adegeye on 28/06/2016.
 */
@SuppressLint("ValidFragment")
public class AcidentsBottomSheetDialog extends BottomSheetDialogFragment {


    private static  int clickCount;
    private static  int positionArrays;
    private TextView tvTitle, tvDetail;
    private ImageView ivImg;
    private TextView dateShow;
    private TextView timeAcident;
    private TextView tvTime;
    private TextView tvDate;
    private TextView tvAddress;
    private ProgressDialog pDialog;
    private ImageView ivIconType;

    public static AcidentsBottomSheetDialog newInstance(int click, int position) {
        clickCount = click;
        positionArrays = position;
        return new AcidentsBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);

        initInstance(rootView);

        showpDialog();

        callBackItem();

        return rootView;
    }

    private void initInstance(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);

        tvTime = (TextView) rootView.findViewById(R.id.tvTime);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
        ivIconType = (ImageView) rootView.findViewById(R.id.ivIconType);

    }

    Geocoder geocoder;
    List<Address> address;
    private void callBackItem() {
        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
        call.enqueue(new Callback<ItemCollectionDao>() {
            @Override
            public void onResponse(Call<ItemCollectionDao> call, retrofit2.Response<ItemCollectionDao> response) {
                if(response.isSuccessful()){
                    hidepDialog();
                    ItemDao dao;
                    ItemCollectionDao CollectionDao = response.body();
                    int sizeDao = CollectionDao.getData().size();
                    //Toast.makeText(Contextor.getInstance().getContext(), sizeDao+dao.getData().get(0).getLat(), Toast.LENGTH_SHORT).show();

                    dao = CollectionDao.getData().get(positionArrays);

                    int id = dao.getId();
                    String subject = dao.getSubject();
                    Date dateCreate = dao.getCreate_date();
                    String latDao = dao.getLat();
                    String lngDao = dao.getLng();
                    String detail = dao.getDetail();
                    String photo = dao.getPhoto();
                    String time = dao.getTime_submit();
                    Date date = dao.getCreate_date();
                    int type = dao.getType();


                    if(clickCount == id){
//                        long date = System.currentTimeMillis();
    
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = sdf.format(dateCreate);
                        //Toast.makeText(Contextor.getInstance().getContext(), "clickCount " + clickCount + "  //id Marker " + id+ "//  all data "+sizeDao, Toast.LENGTH_LONG).show();

                        if(type == 1){
                            ivIconType.setImageResource(R.drawable.a1);
                        }
                        if(type == 2){
                            ivIconType.setImageResource(R.drawable.a2);
                        }

                        if(type == 3){
                            ivIconType.setImageResource(R.drawable.a3);
                        }

                        if(type == 4){
                            ivIconType.setImageResource(R.drawable.a4);
                        }
                        tvTitle.setText(subject);
                        tvDetail.setText(detail);

                        tvDate.setText(dateString);
                        tvTime.setText(time);
                        setImageUrl(photo);


                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            address = geocoder.getFromLocation(Double.parseDouble(latDao) ,Double.parseDouble(lngDao), 1);
                            if(address != null){
                                String  adressme =  address.get(0).getAddressLine(0);
                                String  city =  address.get(0).getLocality();
                                String  state =  address.get(0).getAdminArea();
                                String  country =  address.get(0).getCountryName();
                                String  postalCode =  address.get(0).getPostalCode();
                                String  knowName =  address.get(0).getFeatureName();
                                tvAddress.setText(adressme + city + state + country+ postalCode + knowName);
                            }else{
                                tvAddress.setText("จุดเกิดเหตุ");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(getContext(), "Haven't" + clickCount+"    "+ id, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        hidepDialog();
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(), Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override

            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
                hidepDialog();
                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showpDialog() {
        pDialog = new ProgressDialog(getContext());

        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }


    // setImage load Image
    public void setImageUrl (String url){
        if(!url.equals("not")){
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImg);
            ivImg.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "VISIBLE", Toast.LENGTH_SHORT).show();


        }else {
            ivImg.setVisibility(View.GONE);
            Toast.makeText(getContext(), "gone", Toast.LENGTH_SHORT).show();
        }




    }


}
