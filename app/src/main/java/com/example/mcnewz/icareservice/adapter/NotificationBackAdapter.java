package com.example.mcnewz.icareservice.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemDao;
import com.example.mcnewz.icareservice.view.NewsAcidentsItem;
import com.example.mcnewz.icareservice.view.NotificationBackItem;


/**
 * Created by MCNEWZ on 08-Feb-17.
 */

public class NotificationBackAdapter extends BaseAdapter {
    NotificationBackItemCollectionDao dao;


    public void setDao(NotificationBackItemCollectionDao dao) {
        this.dao = dao;
    }
    @Override
    public int getCount() {

        if(dao == null){
            return 0;
        }
        if (dao.getData() == null)
            return 0;

        return dao.getData().size() ;
//        return 10 ;
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationBackItem item ;
        if (convertView != null) {
            item = (NotificationBackItem) convertView;
        } else {
            item = new NotificationBackItem(parent.getContext());
        }

        NotificationBackItemDao dao = (NotificationBackItemDao) getItem(position);

        item.setTvName(dao.getDepartmentName());
        item.setTvDescription(dao.getDetail());
        item.setImageUrl(dao.getPhoto());
        item.setTime(String.valueOf(dao.getTime_submit()));

        return item;
    }
}
