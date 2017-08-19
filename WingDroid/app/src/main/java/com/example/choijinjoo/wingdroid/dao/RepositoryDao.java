package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class RepositoryDao extends BaseDaoImpl<Repository, Integer> {
    protected RepositoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Repository.class);
    }
}