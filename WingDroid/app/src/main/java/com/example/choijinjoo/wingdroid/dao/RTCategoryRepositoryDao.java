package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.RTCategoryRepository;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class RTCategoryRepositoryDao extends BaseDaoImpl<RTCategoryRepository, Integer> {
    protected RTCategoryRepositoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RTCategoryRepository.class);
    }
}