package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MCNEWZ on 13-Mar-17.
 */

public class SendIDUser {

    @SerializedName("id_user") private String id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
