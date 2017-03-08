package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class ItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<ItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ItemDao> getData() {
        return data;
    }

    public void setData(List<ItemDao> data) {
        this.data = data;
    }
}
