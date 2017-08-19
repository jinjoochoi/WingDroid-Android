package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by choijinjoo on 2017. 8. 18..
 */

public class BaseRepository<T> {
    protected DatabaseHelper dbHelper;

    public BaseRepository() {}

    public BaseRepository(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public void release(){
        OpenHelperManager.releaseHelper();
    }
}
