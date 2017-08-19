package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class RTTagRepositoryDao extends BaseDaoImpl<RTTagRepository, Integer> {
    protected RTTagRepositoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RTTagRepository.class);
    }
}