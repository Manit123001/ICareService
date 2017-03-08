package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 18-Feb-17.
 */

public class TypeAcidentsAlertListItemCollectionDao {

    @SerializedName("success") private boolean success;
    @SerializedName("result") private List<TypeAcidentsAlertListItemDao> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TypeAcidentsAlertListItemDao> getData() {
        return data;
    }

    public void setData(List<TypeAcidentsAlertListItemDao> data) {
        this.data = data;
    }
}
