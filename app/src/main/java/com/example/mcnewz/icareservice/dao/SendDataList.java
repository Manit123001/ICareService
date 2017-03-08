package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MCNEWZ on 20-Feb-17.
 */

public class SendDataList {

    @SerializedName("distance_lat") private String distance_lat;
    @SerializedName("distance_lng") private String distance_lng;

    public String getDistance_lat() {
        return distance_lat;
    }

    public void setDistance_lat(String distance_lat) {
        this.distance_lat = distance_lat;
    }

    public String getDistance_lng() {
        return distance_lng;
    }

    public void setDistance_lng(String distance_lng) {
        this.distance_lng = distance_lng;
    }
}
