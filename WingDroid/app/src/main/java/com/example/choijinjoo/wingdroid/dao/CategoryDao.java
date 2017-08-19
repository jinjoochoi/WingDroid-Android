package com.example.choijinjoo.wingdroid.dao;

import com.example.choijinjoo.wingdroid.model.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CategoryDao extends BaseDaoImpl<Category, Integer> {
    protected CategoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Category.class);
    }
}