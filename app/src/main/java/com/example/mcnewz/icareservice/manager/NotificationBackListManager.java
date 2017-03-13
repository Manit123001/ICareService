package com.example.mcnewz.icareservice.manager;

import android.content.Context;

import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemCollectionDao;
import com.example.mcnewz.icareservice.fragment.NotificationBackFragment;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NotificationBackListManager {


    private Context mContext;
    private NotificationBackItemCollectionDao dao;

    public NotificationBackListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public NotificationBackItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(NotificationBackItemCollectionDao dao) {
        this.dao = dao;
    }
}
