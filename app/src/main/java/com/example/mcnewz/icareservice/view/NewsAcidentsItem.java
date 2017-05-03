package com.example.mcnewz.icareservice.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mcnewz.icareservice.R;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NewsAcidentsItem extends BaseCustomViewGroup {

    private TextView tvTitle, tvName, tvDescription, tvDate ;
    private ImageView ivProfile, ivImg;
    private TextView tvTime;
    private LinearLayout llBackground;


    public NewsAcidentsItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public NewsAcidentsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public NewsAcidentsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public NewsAcidentsItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_acident, this);
    }

    private void initInstances() {
        // findViewById here
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        ivImg = (ImageView) findViewById(R.id.ivImg);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        llBackground = (LinearLayout) findViewById(R.id.llBackground);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    // setSize ViewGroup 2:3
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);// width in px
        int height = width * 2 / 3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY
        );
        // Child View
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        // Self
        setMeasuredDimension(width, height);

    }

    public void  setTitle(String text){
        tvTitle.setText(text);
    }

    public void  setName(String text){
        tvName.setText(text);
    }

    public void  setDescription(String text){
        tvDescription.setText(text);
    }

    public void  setDate(Date createDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(createDate);
        tvDate.setText(dateString);
    }

    public void  setTime(String text){
     tvTime.setText(text);
    }

    public void setProfile(int type){
        if(type == 1){
            ivProfile.setImageResource(R.drawable.a1);
        }
        if(type == 2){
            ivProfile.setImageResource(R.drawable.a2);
        }

        if(type == 3){
            ivProfile.setImageResource(R.drawable.a3);
        }

        if(type == 4){
            ivProfile.setImageResource(R.drawable.a4);
        }
    }


    // setImage load Image
    public void setImageUrl (String url){
        Glide.with(getContext())
                .load(url)
                .error(R.drawable.nav_header_bg)
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);
        ivImg.setVisibility(View.VISIBLE);
    }
}
