package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class CategoryRepository extends BaseRepository {
    private final String TAG = "CategoryRepository";
    private CategoryDao categoryDao;

    public CategoryRepository(Context context) {
        super(context);
        categoryDao = dbHelper.getCategoryDao();
    }

    public List<Category> getCategories() {
        List<Category> results = new ArrayList<>();
        try {
            results.addAll(categoryDao.queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }

    public Category getCategoryByName(String name) {
        try {
            return categoryDao.queryBuilder().where().eq("name",name).queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public List<Category> getCategoriesOrderByName() {
        List<Category> results = new ArrayList<>();
        try {
            results.addAll(categoryDao.queryBuilder().orderBy("name",true).query());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }



    public void createOrUpdateCategory(Category category) {
        try {
            categoryDao.createOrUpdate(category);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateCategory(Category category) {
        try {
            categoryDao.update(category);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void deleteCategory(Category category) {
        try {
            categoryDao.delete(category);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}