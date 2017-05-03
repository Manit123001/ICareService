package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class HistorySendItemCollectionDao {

    @SerializedName("result") private List<HistorySendItemDao> data;

    public List<HistorySendItemDao> getData() {
        return data;
    }

    public void setData(List<HistorySendItemDao> data) {
        this.data = data;
    }
}
