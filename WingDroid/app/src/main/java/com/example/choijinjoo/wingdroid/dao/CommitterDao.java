package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.Committer;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CommitterDao extends BaseDaoImpl<Committer, Integer> {
    protected CommitterDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Committer.class);
    }
}