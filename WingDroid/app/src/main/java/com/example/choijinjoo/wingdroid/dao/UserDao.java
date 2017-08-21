package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class UserDao extends BaseDaoImpl<User, Integer> {
    protected UserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }
}