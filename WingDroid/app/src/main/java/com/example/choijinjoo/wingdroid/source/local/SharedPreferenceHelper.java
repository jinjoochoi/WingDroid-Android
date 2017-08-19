package com.example.choijinjoo.wingdroid.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

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

    public void putStringSetValue(Context context, String key, Set<String> value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        sharedPreferences.edit().putStringSet(key, value).apply();
    }

    public void putStringValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getStringValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        return sharedPreferences.getString(key, value);
    }

    public Set<String> getStringSetValue(Context context, String key, String def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        Set<String> defSet = new HashSet<>();
        defSet.add(def);
        return sharedPreferences.getStringSet(key, defSet);
    }

    public Long getLongValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        return sharedPreferences.getLong(key, 0);
    }

    public void putLongValue(Context context, String key, Long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        sharedPreferences.edit().putLong(key, value).apply();
    }


    public static class Config {
        public static String AUTH_TOKEN = "auth_token";
        public static String FCM_TOKEN = "fcm_token";
        public static String BOOKMARK_CATEGORY_FILTER = "bookmark_category_filter";
        public static String LOADED_CATEGORY = "loaded_category";
        public static String LOADED_REPOSITORY = "loaded_repository";
        public static String LOADED_TAG = "loaded_tag";
        public static String LOAD_UPDATEDAT = "load_updatedat";
        public static String LOAD= "load";
    }

}
