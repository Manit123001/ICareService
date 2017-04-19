package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MCNEWZ on 13-Mar-17.
 */

public class SendIDUser {

    @SerializedName("upIdUser") private String upIdUser;

    public String getUpIdUser() {
        return upIdUser;
    }

    public void setUpIdUser(String upIdUser) {
        this.upIdUser = upIdUser;
    }
}
