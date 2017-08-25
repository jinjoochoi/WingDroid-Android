package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.model.event.Release;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.choijinjoo.wingdroid.model.event.Release.ID_FIELD;
import static com.example.choijinjoo.wingdroid.model.event.Release.PUBLISHEAT_FIELD;
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
            for (Release release : releases) {
                User author = userDao.queryBuilder().where().eq("id", release.getAuthor().getId()).queryForFirst();
                release.setAuthor(author);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, release.getRepository().getId()).queryForFirst();
                release.setRepository(repository);
                results.add(release);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Release getLastRelease(Repository repository) {
        try {
            return releaseDao.queryBuilder().orderBy(PUBLISHEAT_FIELD, false).where().eq("repository_id", repository.getId()).queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public Observable<List<Event>> getEvents() {
        List<Event> results = new ArrayList<>();
        try {
            List<Release> releases = releaseDao.queryBuilder().orderBy(UPDATEDAT_FIELD, false).query();
            for (Release release : releases) {
                User author = userDao.queryBuilder().where().eq("id", release.getAuthor().getId()).queryForFirst();
                release.setAuthor(author);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, release.getRepository().getId()).queryForFirst();
                release.setRepository(repository);
                results.add(new Event(release));
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Release getReleaseById(Release release) {
        try {
            Release result = releaseDao.queryBuilder().where().eq(ID_FIELD, release.getId()).queryForFirst();
            User author = userDao.queryBuilder().where().eq("id", result.getAuthor().getId()).queryForFirst();
            result.setAuthor(author);
            Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, result.getRepository().getId()).queryForFirst();
            result.setRepository(repository);
            return result;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public void createOrUpdateRelease(Release release) {
        try {
            releaseDao.createOrUpdate(release);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateRelease(Release release) {
        try {
            releaseDao.update(release);
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