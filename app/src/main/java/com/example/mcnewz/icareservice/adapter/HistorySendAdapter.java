package com.example.mcnewz.icareservice.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mcnewz.icareservice.dao.HistorySendItemCollectionDao;
import com.example.mcnewz.icareservice.dao.HistorySendItemDao;
import com.example.mcnewz.icareservice.view.HistorySendItem;

/**
 * Created by MCNEWZ on 02-May-17.
 */

public class HistorySendAdapter extends BaseAdapter {

    private HistorySendItemCollectionDao dao;

    public void setDao(HistorySendItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null){
            return 0;
        }else if(dao.getData()== null){
            return 0;
        }

        return dao.getData().size();
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
        HistorySendItem item ;

        if(convertView!= null ){
            item = (HistorySendItem) convertView;
        }else {
            item = new HistorySendItem(parent.getContext());
        }


        HistorySendItemDao itemDao = (HistorySendItemDao)getItem(position);
        item.setTvName(itemDao.getSubject());
        item.setTvDescription(itemDao.getDetail());
        item.setImageUrl(itemDao.getPhoto());


        item.setTime(String.valueOf(itemDao.getTime_submit()));

        return item;
    }

}
