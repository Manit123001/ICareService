package com.example.mcnewz.icareservice.fragment;

import android.annotation.SuppressLint;
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
        callBackItem();

        return rootView;
    }

    private void initInstance(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);

        tvTime = (TextView) rootView.findViewById(R.id.tvTime);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);

    }


    private void callBackItem() {
        Call<ItemCollectionDao> call = HttpManager.getInstance().getService().loadItemList();
        call.enqueue(new Callback<ItemCollectionDao>() {
            @Override
            public void onResponse(Call<ItemCollectionDao> call, retrofit2.Response<ItemCollectionDao> response) {
                if(response.isSuccessful()){
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

                    if(clickCount == id){
//                        long date = System.currentTimeMillis();
    
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = sdf.format(dateCreate);
                        //Toast.makeText(Contextor.getInstance().getContext(), "clickCount " + clickCount + "  //id Marker " + id+ "//  all data "+sizeDao, Toast.LENGTH_LONG).show();

                        tvTitle.setText(subject);
                        tvDetail.setText(detail);

                        tvDate.setText(dateString);
                        tvTime.setText(time);
                        setImageUrl(photo);

                    } else {
                        Toast.makeText(getContext(), "Haven't" + clickCount+"    "+ id, Toast.LENGTH_SHORT).show();
                    }

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
            public void onFailure(Call<ItemCollectionDao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString()+"error 555", Toast.LENGTH_LONG).show();
            }
        });
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
