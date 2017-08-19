package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;

import java.sql.SQLException;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class RTTagRepositoryRepository extends BaseRepository {
    private final String TAG = "RepositoryRepository";
    private RTTagRepositoryDao rtTagRepositoryDao;

    public RTTagRepositoryRepository(Context context) {
        super(context);
        rtTagRepositoryDao = dbHelper.getRTTagRepositoryDao();
    }


    public void createOrUpdateRepository(Repository repository) {
        try {
            for (Tag tag : repository.getTags()) {
                rtTagRepositoryDao.createOrUpdate(new RTTagRepository(repository, tag));
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void deleteRepository(RTTagRepository repository) {
        try {
            rtTagRepositoryDao.delete(repository);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}