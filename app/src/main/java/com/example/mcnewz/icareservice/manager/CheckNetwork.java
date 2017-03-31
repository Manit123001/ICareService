package com.example.mcnewz.icareservice.manager;

/**
 * Created by MCNEWZ on 11-Mar-17.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/****************
 * Inner Class
 *****************/

// Check Internet Connect
public class CheckNetwork {
    private Context context;

    public CheckNetwork(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}