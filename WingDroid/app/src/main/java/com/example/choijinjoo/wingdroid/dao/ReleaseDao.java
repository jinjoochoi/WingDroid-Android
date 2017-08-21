package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.event.Release;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class ReleaseDao extends BaseDaoImpl<Release, Integer> {
    protected ReleaseDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Release.class);
    }
}