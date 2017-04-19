package com.example.mcnewz.icareservice.manager.http;


import com.example.mcnewz.icareservice.dao.DepartmentsItemCollectionDao;
import com.example.mcnewz.icareservice.dao.ItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NeaRegencyListItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NearRegencyListItemDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemCollectionDao;
import com.example.mcnewz.icareservice.dao.NotificationBackItemDao;
import com.example.mcnewz.icareservice.dao.RegencyInfoItemDao;
import com.example.mcnewz.icareservice.dao.SendDataList;
import com.example.mcnewz.icareservice.dao.TypeAcidentsAlertListItemCollectionDao;
import com.example.mcnewz.icareservice.dao.WarningInfoItemDao;
import com.example.mcnewz.icareservice.dao.WarningItemCollectionDao;

import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public interface ApiService {

    @POST("icareservice/jsonAcidentShowMain.php")
    Call<ItemCollectionDao> loadItemList();

    @POST("icareservice/jsonWarningShowMain.php")
    Call<WarningItemCollectionDao> loadWarningItemList();

    // Marker Department
    @POST("department/DepartmentsData.php")
    Call<DepartmentsItemCollectionDao> loadItemListDepartment();

    // List Near Department
    @POST("icareservice/jsonTypeAcidentsAlert.php")
    Call<TypeAcidentsAlertListItemCollectionDao> loadTypeAcidentsAlert();

    // List Near Department
    @FormUrlEncoded
    @POST("icareservice/jsonDepartmentNearList.php")
    Call<NeaRegencyListItemCollectionDao> loadItemListNearDepartment(
            @Field("distance_lat") String distance_lat,
            @Field("distance_lng") String distance_lng
    );

    // Insert Data Acidents
    @FormUrlEncoded
    @POST("icareservice/insertRegencyInfo.php")
    Call<RegencyInfoItemDao> setRegencyInfoList(
            @Field("ac_detail") String acDetail,
            @Field("ac_latitude") String acLatitude,
            @Field("ac_longtitude") String acLongtitude,
            @Field("members_member_id") int membersMemberId,
            @Field("types_type_id") int typesTypeId,
            @Field("imagePhoto") String imageName,
            @Field("sumDepartmentSelect") String sumDepartmentSelect,
            @Field("ac_Subject") String acSubject,
            @Field("ac_time_submit") String time_submit,
            @Field("ac_create_date") Date createDate
    );

    // Insert Data Warning
    @FormUrlEncoded
    @POST("icareservice/insertWarningInfo.php")
    Call<WarningInfoItemDao> setWarningInfoList(
            @Field("warn_detail") String warnDetail,
            @Field("warn_latitude") String warnLatitude,
            @Field("warn_longtitude") String warnLongtitude,
            @Field("members_member_id") int membersMemberId,
            @Field("warn_photo") String warnPhoto,
            @Field("warn_subject_type") String warnSubjectType,
            @Field("warn_time_submit") String warnTimeSubmit,
            @Field("warn_create_date") Date warnCreateDate
    );


    // Insert Data Acidents
    @FormUrlEncoded
    @POST("icareservice/jsonNotificationBack.php")
    Call<NotificationBackItemCollectionDao> setNotificationBack(
            @Field("upIdUser") String upIdUser
    );


}



















