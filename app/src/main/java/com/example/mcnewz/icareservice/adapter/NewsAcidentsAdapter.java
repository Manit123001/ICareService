package com.example.mcnewz.icareservice.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemDao;
import com.example.mcnewz.icareservice.view.NewsAcidentsItem;

import java.sql.Date;


/**
 * Created by MCNEWZ on 08-Feb-17.
 */

public class NewsAcidentsAdapter extends BaseAdapter {
   ItemCollectionDao dao;


    public void setDao(ItemCollectionDao dao) {
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
        NewsAcidentsItem item ;
        if (convertView != null) {
            item = (NewsAcidentsItem) convertView;
        } else {
            item = new NewsAcidentsItem(parent.getContext());
        }

        ItemDao dao = (ItemDao) getItem(position);

        item.setTitle(dao.getSubject()+"( "+dao.getLat()+dao.getLng()+ " )");
        item.setName(dao.getSubject());
        item.setDescription(dao.getDetail());
        item.setImageUrl(dao.getPhoto());
        item.setTime(String.valueOf(dao.getTime_submit()));
        item.setDate(dao.getCreate_date());
        return item;
    }
}
