package com.example.mcnewz.icareservice.manager;

import android.content.Context;

import com.example.mcnewz.icareservice.manager.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MCNEWZ on 24-Jan-17.
 */

public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if ( instance == null )
            instance = new HttpManager();
        return  instance;
    }

    private Context mContext;

    private ApiService service;

    private HttpManager() {
        mContext = Contextor.getInstance().getContext();

        Gson gson = new GsonBuilder()

                .setDateFormat("yyyy-MM-dd")
                .setDateFormat("'T'HH:mm:ssz")

                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://icareuserver.comscisau.com/icare/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }
}
