package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class NotificationBackItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<NotificationBackItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<NotificationBackItemDao> getData() {
        return data;
    }

    public void setData(List<NotificationBackItemDao> data) {
        this.data = data;
    }
}
