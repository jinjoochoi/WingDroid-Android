package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class SearchHistoryDao extends BaseDaoImpl<SearchHistory, Integer> {
    protected SearchHistoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SearchHistory.class);
    }
}