package com.example.mcnewz.icareservice.manager;

import android.content.Context;

import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NewsAcidentsListManager {


    private Context mContext;
    private ItemCollectionDao dao;

    public NewsAcidentsListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public ItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(ItemCollectionDao dao) {
        this.dao = dao;
    }
}
