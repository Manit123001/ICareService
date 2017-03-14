package com.example.mcnewz.icareservice.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.example.mcnewz.icareservice.dao.DepartmentsItemCollectionDao;
import com.example.mcnewz.icareservice.dao.DepartmentsItemDao;
import com.example.mcnewz.icareservice.manager.HttpManager;
import com.google.android.gms.maps.model.LatLng;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayowa.adegeye on 28/06/2016.
 */

@SuppressLint("ValidFragment")
public class DepartmentsBottomSheetDialog extends BottomSheetDialogFragment {

    private static  int clickCount;
    private static  int positionArrays;
    private TextView tvTitle, tvDetail;
    private ImageView ivImg;
    private TextView dateShow;
    private TextView timeAcident;
    private ImageView ic_image;
    private TextView tvTel;

    public static DepartmentsBottomSheetDialog newInstance(int click, int indexArray) {
        clickCount = click;
        positionArrays = indexArray;
        return new DepartmentsBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_department_bottom_sheet, container, false);


        initInstance(rootView);
        callBackItemDepartments();
        return rootView;
    }

    private void initInstance(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvTel = (TextView) rootView.findViewById(R.id.tvTelDetail);
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);

        ic_image  = (ImageView)rootView.findViewById(R.id.ic_image);
    }

//    private void callBackItem() {
//        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
//        call.enqueue(new Callback<ItemCollectionDao>() {
//            @Override
//            public void onResponse(Call<ItemCollectionDao> call, retrofit2.Response<ItemCollectionDao> response) {
//                if(response.isSuccessful()){
//                    ItemDao dao;
//                    ItemCollectionDao CollectionDao = response.body();
//                    int sizeDao = CollectionDao.getData().size();
//                    //Toast.makeText(Contextor.getInstance().getContext(), sizeDao+dao.getData().get(0).getLat(), Toast.LENGTH_SHORT).show();
//
//                    dao = CollectionDao.getData().get(positionArrays);
//
//                    int id = dao.getId();
//                    String subject = dao.getSubject();
//                    Date dateCreate = dao.getCreate_date();
//                    String latDao = dao.getLat();
//                    String lngDao = dao.getLng();
//                    String detail = dao.getDetail();
//                    String photo = dao.getPhoto();
//
//                    if(clickCount == id){
////                        long date = System.currentTimeMillis();
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                        String dateString = sdf.format(dateCreate);
//                        Toast.makeText(getContext(), "clickCount " + clickCount + "  //id Marker " + id+ "//  all data "+sizeDao, Toast.LENGTH_LONG).show();
//
//                        tvTitle.setText(subject);
//                        tvDetail.setText(detail);
//
//
//                        dateShow.setText(dateString);
//                        setImageUrl(photo);
//
//                    } else {
//                        Toast.makeText(getContext(), "Haven't" + clickCount+"    "+ id, Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    try {
//                        Toast.makeText(Contextor.getInstance().getContext(),
//                                response.errorBody().string(), Toast.LENGTH_LONG).show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
//                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error 555", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void callBackItemDepartments() {
        Call<DepartmentsItemCollectionDao> call = HttpManager.getInstance().getService().loadItemListDepartment();
        call.enqueue(new Callback<DepartmentsItemCollectionDao>() {
            @Override
            public void onResponse(Call<DepartmentsItemCollectionDao> call, Response<DepartmentsItemCollectionDao> response) {
                DepartmentsItemDao dao ;
                DepartmentsItemCollectionDao collectionDao = response.body();
                int sizeDao = collectionDao.getData().size();

                for (int i = 0; i < sizeDao; i++){

                    dao = collectionDao.getData().get(i);

                    int id = dao.getId();
                    String name = dao.getName();
                    String detail = dao.getDescription();

                    String latDao = dao.getLatitude();
                    String lngDao = dao.getLongtitude();
                    String detailDao = dao.getDescription();
                    final String tel = dao.getTel();
                    int type = dao.getTypeId();

                    float typeAc = 0;
                    LatLng latLng = new LatLng(Double.parseDouble(latDao), Double.parseDouble(lngDao));
//                    Toast.makeText(getContext(), "123456789"+latLng + type, Toast.LENGTH_SHORT).show();
                    int typeDe;

                    if(clickCount == id){
                        if(type == 1){
                            typeDe = R.drawable.ic_depart_police;


                        } else if (type == 2){
                            typeDe = R.drawable.ic_depart_hospital;


                        }else if (type == 3){
                            typeDe = R.drawable.ic_depart_fire;


                        }else {
                            typeDe = R.drawable.ic_depart_wor;

                        }

                        tvTitle.setText(name);
                        tvTel.setText(tel);
                        tvTel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                actionCall(tel);
                            }
                        });
                        tvDetail.setText(detail);
                        ic_image.setImageResource(typeDe);
                        //Toast.makeText(Contextor.getInstance().getContext(), "clickCount" + clickCount+" = "+ id, Toast.LENGTH_SHORT).show();

                    }
                }
            }


            @Override
            public void onFailure(Call<DepartmentsItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"DepartmentsItemCollectionDao Erorr", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void actionCall(String callUrl) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callUrl.trim()));
        startActivity(intent);
    }


    // setImage load Image
    public void setImageUrl (String url){
        Glide.with(getContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);
    }


}
