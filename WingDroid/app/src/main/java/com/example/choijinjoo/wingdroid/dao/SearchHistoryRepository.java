package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.model.eventbus.SearchHistoryEvent;
import com.j256.ormlite.dao.Dao;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class SearchHistoryRepository extends BaseRepository {
    private final String TAG = "RepositoryRepository";
    private SearchHistoryDao searchHistoryDao;

    public SearchHistoryRepository(Context context) {
        super(context);
        searchHistoryDao = dbHelper.getSearchHistoryDao();
    }


    public void addSearchHistory(SearchHistory searchHistory) {
        try {
           searchHistoryDao.create(searchHistory);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void deleteSearchHistory(SearchHistory searchHistory) {
        try {
            searchHistoryDao.delete(searchHistory);
            EventBus.getDefault().post(new SearchHistoryEvent());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Observable<List<SearchHistory>> getSearchHistories() {
        List<SearchHistory> results = new ArrayList<>();
        try {
            results.addAll(searchHistoryDao.queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public void registerDaoObserver(Dao.DaoObserver observer) {
        searchHistoryDao.registerObserver(observer);
    }

    public void unregisterDaoObserver(Dao.DaoObserver observer) {
        searchHistoryDao.unregisterObserver(observer);
    }
}