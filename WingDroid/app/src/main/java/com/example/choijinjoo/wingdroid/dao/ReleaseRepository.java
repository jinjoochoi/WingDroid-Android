package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.model.event.Release;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.choijinjoo.wingdroid.model.event.Release.UPDATEDAT_FIELD;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class ReleaseRepository extends BaseRepository {
    private final String TAG = "ReleaseRepository";
    private ReleaseDao releaseDao;
    private UserDao userDao;
    private RepositoryDao repositoryDao;

    public ReleaseRepository(Context context) {
        super(context);
        releaseDao = dbHelper.getReleaseDao();
        userDao = dbHelper.getUserDao();
        repositoryDao = dbHelper.getRepoDao();
    }

    public Observable<List<Release>> getReleases() {
        List<Release> results = new ArrayList<>();
        try {
            List<Release> releases = releaseDao.queryBuilder().orderBy(UPDATEDAT_FIELD, false).query();
            for(Release release : releases){
                User author = userDao.queryBuilder().where().eq("id",release.getAuthor().getId()).queryForFirst();
                release.setAuthor(author);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD,release.getRepository().getId()).queryForFirst();
                release.setRepository(repository);
                results.add(release);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }


    public void createOrUpdateRelease(Release release) {
        try {
            releaseDao.createOrUpdate(release);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void createOrUpdateReleases(List<Release> releases) {
        try {
            for (Release release : releases) {
                releaseDao.createOrUpdate(release);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


}