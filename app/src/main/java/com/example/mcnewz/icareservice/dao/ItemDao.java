package com.example.mcnewz.icareservice.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class ItemDao implements Parcelable {

    @SerializedName("id") private int id;
    @SerializedName("subject") private String subject;
    @SerializedName("detail") private String detail;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("photo") private String photo;
    @SerializedName("vedio") private String vedio;
    @SerializedName("create_date") private Date create_date;
    @SerializedName("time_submit") private String time_submit;
    @SerializedName("time_incident") private String time_incident;
    @SerializedName("members") private int members;
    @SerializedName("type") private int type;

    protected ItemDao(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        detail = in.readString();
        lat = in.readString();
        lng = in.readString();
        photo = in.readString();
        vedio = in.readString();
        create_date = Date.valueOf(in.readString());
        time_submit = in.readString();
        time_incident = in.readString();
        members = in.readInt();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subject);
        dest.writeString(detail);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(photo);
        dest.writeString(vedio);
        dest.writeString(String.valueOf(create_date));
        dest.writeString(time_submit);
        dest.writeString(time_incident);
        dest.writeInt(members);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemDao> CREATOR = new Creator<ItemDao>() {
        @Override
        public ItemDao createFromParcel(Parcel in) {
            return new ItemDao(in);
        }

        @Override
        public ItemDao[] newArray(int size) {
            return new ItemDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getTime_submit() {
        return time_submit;
    }

    public void setTime_submit(String time_submit) {
        this.time_submit = time_submit;
    }

    public String getTime_incident() {
        return time_incident;
    }

    public void setTime_incident(String time_incident) {
        this.time_incident = time_incident;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
