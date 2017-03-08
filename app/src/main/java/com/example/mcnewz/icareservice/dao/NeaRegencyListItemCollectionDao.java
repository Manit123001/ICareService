package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class NeaRegencyListItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<NearRegencyListItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<NearRegencyListItemDao> getData() {
        return data;
    }

    public void setData(List<NearRegencyListItemDao> data) {
        this.data = data;
    }
}
