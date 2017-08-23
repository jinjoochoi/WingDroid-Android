package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.event.Commit;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.choijinjoo.wingdroid.model.event.Commit.ID_FIELD;
import static com.example.choijinjoo.wingdroid.model.event.Commit.TIME_STAMP;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class CommitRepository extends BaseRepository {
    private final String TAG = "CommitRepository";
    private CommitDao commitDao;
    private UserDao userDao;
    private RepositoryDao repositoryDao;
    private CommitterDao committerDao;

    List<CommitChangeListener> listeners = new ArrayList<>();

    public static CommitRepository instance = null;

    public static CommitRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CommitRepository(context.getApplicationContext());
        }
        return instance;
    }

    public interface CommitChangeListener {
        void changed();
    }


    public CommitRepository(Context context) {
        super(context);
        commitDao = dbHelper.getCommitDao();
        userDao = dbHelper.getUserDao();
        repositoryDao = dbHelper.getRepoDao();
        committerDao = dbHelper.getCommiterDao();
    }

    public Observable<List<Commit>> getCommits() {
        List<Commit> results = new ArrayList<>();
        try {
            List<Commit> commits = commitDao.queryBuilder().orderBy(TIME_STAMP, false).query();
            for (Commit commit : commits) {
                Committer committer = committerDao.queryBuilder().where().eq("id", commit.getCommitter().getId()).queryForFirst();
                commit.setCommitter(committer);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, commit.getRepository().getId()).queryForFirst();
                commit.setRepository(repository);
                results.add(commit);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Commit getLastCommit(Repository repository) {
        try {
            QueryBuilder<Repository, Integer> repositoryQB = repositoryDao.queryBuilder();
            SelectArg repoSelectArg = new SelectArg();
            repositoryQB.where().eq(Repository.ID_FIELD,repoSelectArg);
            QueryBuilder<Commit,Integer> commitQB = commitDao.queryBuilder();
            commitQB.join(repositoryQB);

            repoSelectArg.setValue(repository);
            return commitQB.join(repositoryQB).orderBy(Commit.ID_FIELD,false).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Observable<List<Event>> getEvents() {
        List<Event> results = new ArrayList<>();
        try {
            List<Commit> commits = commitDao.queryBuilder().orderBy(TIME_STAMP, false).query();
            for (Commit commit : commits) {
                Committer committer = committerDao.queryBuilder().where().eq("id", commit.getCommitter().getId()).queryForFirst();
                commit.setCommitter(committer);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, commit.getRepository().getId()).queryForFirst();
                commit.setRepository(repository);
                results.add(new Event(commit));
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Commit getCommitById(Commit commit) {
        try {
            Commit result = commitDao.queryBuilder().where().eq(ID_FIELD, commit.getUrl()).queryForFirst();
            Committer committer = committerDao.queryBuilder().where().eq("id", result.getCommitter().getId()).queryForFirst();
            result.setCommitter(committer);
            Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, result.getRepository().getId()).queryForFirst();
            result.setRepository(repository);
            return result;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }


    public void updateCommit(Commit commit) {
        try {
            commitDao.update(commit);
            notifyRegisterChanged();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void createOrUpdateCommit(Commit commit) {
        try {
            commitDao.createOrUpdate(commit);

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void notifyRegisterChanged() {
        for (CommitChangeListener listener : listeners) {
            listener.changed();
        }
    }


}