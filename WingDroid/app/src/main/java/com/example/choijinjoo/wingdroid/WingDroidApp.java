package com.example.choijinjoo.wingdroid;

import android.app.Application;

import com.example.choijinjoo.wingdroid.source.RepositoryLocalSource;
import com.example.choijinjoo.wingdroid.source.SearchHistoryLocalSource;

import io.realm.Realm;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class WingDroidApp extends Application {
    private static WingDroidApp instance;

    SearchHistoryLocalSource searchHistoryLocalSource;
    RepositoryLocalSource repositoryLocalSource;

    public static WingDroidApp getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);

    }

    public final SearchHistoryLocalSource searchHistoryLocalSource() {
        return searchHistoryLocalSource == null ? searchHistoryLocalSource = createSearchHistoryLocalSource() : searchHistoryLocalSource;
    }

    public final RepositoryLocalSource repositoryLocalSource() {
        return repositoryLocalSource == null ? repositoryLocalSource = createRepositoryLocalSource() : repositoryLocalSource;
    }

    private SearchHistoryLocalSource createSearchHistoryLocalSource(){
        return new SearchHistoryLocalSource();
    }

    private RepositoryLocalSource createRepositoryLocalSource(){
        return new RepositoryLocalSource();
    }
}
