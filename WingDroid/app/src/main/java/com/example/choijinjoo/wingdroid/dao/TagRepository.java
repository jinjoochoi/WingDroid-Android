package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class TagRepository extends BaseRepository {
    private final String TAG = "TagRepository";
    private TagDao tagDao;

    public TagRepository(Context context) {
        super(context);
        tagDao = dbHelper.getTagDao();
    }


    public List<Tag> getTags() {
        List<Tag> results = new ArrayList<>();
        try {
            results.addAll(tagDao.queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }

    public void createOrUpdateTag(Tag tag) {
        try {
            tagDao.createOrUpdate(tag);

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateTag(Tag tag) {
        try {
            tagDao.update(tag);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void deleteTag(Tag tag) {
        try {
            tagDao.delete(tag);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}