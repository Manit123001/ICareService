package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MCNEWZ on 18-Feb-17.
 */

public class TypeAcidentsAlertListItemDao {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
