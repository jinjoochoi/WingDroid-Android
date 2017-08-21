package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.User;

import java.sql.SQLException;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class UserRepository extends BaseRepository {
    private final String TAG = "UserRepository";
    private UserDao userDao;

    public UserRepository(Context context) {
        super(context);
        userDao = dbHelper.getUserDao();
    }


    public User createOrUpdateUser(User user) {
        try {
            userDao.createOrUpdate(user);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return user;
    }

}