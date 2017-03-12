package com.example.mcnewz.icareservice.jamelogin.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by MCNEWZ on 12-Mar-17.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();

    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}
