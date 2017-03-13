package com.example.mcnewz.icareservice.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
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
public class NotificationBackItem extends BaseCustomViewGroup {

    private TextView tvTitle, tvName, tvDescription, tvDate ;
    private ImageView ivProfile, ivRegency;
    private TextView tvTime;


    public NotificationBackItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public NotificationBackItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public NotificationBackItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public NotificationBackItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_notification_back, this);
    }

    private void initInstances() {
        // findViewById here
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDescript);
        tvTime = (TextView) findViewById(R.id.tvTime);
        ivRegency = (ImageView) findViewById(R.id.ivRegency);

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



    public void  setTvName(String text){

        tvName.setText(text);
    }

    public void  setTvDescription(String text){

        tvDescription.setText(text);
    }


    public void  setTime(String text){

     tvTime.setText(text);
    }


    // setImage load Image
    public void setImageUrl (String url){
//        Glide.with(getContext())
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(ivRegency);

    }
}
