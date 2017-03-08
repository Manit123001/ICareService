package com.example.mcnewz.icareservice.dao;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by MCNEWZ on 04-Feb-17.
 */

public class NearRegencyListItemDao {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("description") private String description;
    @SerializedName("tel") private String tel;
    @SerializedName("email") private String email;
    @SerializedName("address") private String address;
    @SerializedName("latitude") private String latitude;
    @SerializedName("longtitude") private String longtitude;
    @SerializedName("created_date") private Date createDate;
    @SerializedName("type_id") private int typeId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
