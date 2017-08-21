package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.Bookmark;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class BookmarkDao extends BaseDaoImpl<Bookmark, Integer> {
    protected BookmarkDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Bookmark.class);
    }
}