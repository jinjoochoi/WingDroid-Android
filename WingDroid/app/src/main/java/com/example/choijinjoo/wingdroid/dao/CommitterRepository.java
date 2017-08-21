package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Committer;

import java.sql.SQLException;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class CommitterRepository extends BaseRepository {
    private final String TAG = "CommitterRepository";
    private CommitterDao committerDao;

    public CommitterRepository(Context context) {
        super(context);
        committerDao = dbHelper.getCommiterDao();
    }


    public Committer createOrUpdateCommitter(Committer committer) {
        try {
            committerDao.createOrUpdate(committer);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return committer;
    }

}