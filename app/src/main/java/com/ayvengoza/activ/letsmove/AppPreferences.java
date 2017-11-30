package com.ayvengoza.activ.letsmove;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ang on 30.11.17.
 */

public class AppPreferences {
    private static final String PREF_START_TIME = "StartTime";

    public static long getStartTime(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getLong(PREF_START_TIME, 0);
    }

    public static void setStartTime(Context context, long time){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putLong(PREF_START_TIME, time).commit();
    }
}
