package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.Tag;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class TagDao extends BaseDaoImpl<Tag, Integer> {
    protected TagDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Tag.class);
    }
}