package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 10-Mar-17.
 */

public class WarningItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<WarningItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<WarningItemDao> getData() {
        return data;
    }

    public void setData(List<WarningItemDao> data) {
        this.data = data;
    }
}
