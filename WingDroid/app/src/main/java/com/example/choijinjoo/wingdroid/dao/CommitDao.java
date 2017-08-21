package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.event.Commit;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CommitDao extends BaseDaoImpl<Commit, Integer> {
    protected CommitDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Commit.class);
    }
}