package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.event.Commit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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
//                User author = userDao.queryBuilder().where().eq("id", commit.getAuthor().getId()).queryForFirst();
//                commit.setAuthor(author);
                Committer committer = committerDao.queryBuilder().where().eq("id",commit.getCommitter().getId()).queryForFirst();
                commit.setCommitter(committer);
                Repository repository = repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD,commit.getRepository().getId()).queryForFirst();
                commit.setRepository(repository);
                results.add(commit);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public void createOrUpdateCommit(Commit commit) {
        try {
            commitDao.createOrUpdate(commit);

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


}