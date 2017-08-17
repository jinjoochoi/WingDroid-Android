package com.example.choijinjoo.wingdroid.source.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by choijinjoo on 2017. 7. 14..
 */

public class SharedPreferenceHelper {
    private final static String PREF_FILE = "PREF";
    private static SharedPreferenceHelper instance = null;

    public static SharedPreferenceHelper getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceHelper();
        }
        return instance;
    }

    public void putBooleanValue(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void putStringValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        sharedPreferences.edit().putString(key, value).apply();
    }
    public String getStringValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        return sharedPreferences.getString(key, value);
    }

    public static class Config{
        public static String AUTH_TOKEN = "auth_token";
        public static String FCM_TOKEN = "fcm_token";
    }

}
