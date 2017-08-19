package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.RTCategoryRepository;
import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.choijinjoo.wingdroid.model.Repository.DESCRIPTION_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.NAME_FIELD;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class RepositoryRepository extends BaseRepository {
    private final String TAG = "RepositoryRepository";
    private TagDao tagDao;
    private CategoryDao categoryDao;
    private RepositoryDao repositoryDao;
    private RTTagRepositoryDao rtTagRepositoryDao;
    private RTCategoryRepositoryDao rtCategoryRepositoryDao;
    private PreparedQuery<Tag> tagForRepoPreparedQuery = null;
    private PreparedQuery<Repository> repoForTagPreparedQuery = null;
    private PreparedQuery<Repository> repoForCategoryPreparedQuery = null;
    private PreparedQuery<Repository> repoForCategoryOrderByStarPreparedQuery = null;

    public RepositoryRepository(Context context) {
        super(context);
        repositoryDao = dbHelper.getRepoDao();
        rtTagRepositoryDao = dbHelper.getRTTagRepositoryDao();
        rtCategoryRepositoryDao = dbHelper.getRTCategoryRepositoryDao();
        tagDao = dbHelper.getTagDao();
        categoryDao = dbHelper.getCategoryDao();

    }

    public Repository getRepositoryById(String repoId) {
        Repository result = null;
        try {
            result = setTagsForRepo(repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, repoId).queryForFirst());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    public List<Repository> getRepositories() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryForAll()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }


    private Repository setTagsForRepo(Repository repository) {
        Repository result = null;
        try {
            if (tagForRepoPreparedQuery == null)
                tagForRepoPreparedQuery = makeTagForRepoQuery();

            tagForRepoPreparedQuery.setArgumentHolderValue(0, repository);
            List<Tag> tagsForRepo = tagDao.query(tagForRepoPreparedQuery);
            repository.setTags(tagsForRepo);

            result = repository;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Repository> setTagsForRepo(List<Repository> repositories) {
        try {
            if (tagForRepoPreparedQuery == null)
                tagForRepoPreparedQuery = makeTagForRepoQuery();

            for (Repository repository : repositories) {
                tagForRepoPreparedQuery.setArgumentHolderValue(0, repository);
                List<Tag> tagsForRepo = tagDao.query(tagForRepoPreparedQuery);
                repository.setTags(tagsForRepo);
            }

            return repositories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //TODO : distinct
    public List<Repository> getRepositoryByText(String text) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForTagPreparedQuery == null)
                repoForTagPreparedQuery = makeRepoForTag();

            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeRepoForTag();

            if(repoForCategoryOrderByStarPreparedQuery == null)
                repoForCategoryOrderByStarPreparedQuery = makeRepoForCategoryOrderByStarQuery();

            // search with repo's name, repo's description
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().where().like(DESCRIPTION_FIELD, text + "%").or().like(NAME_FIELD, text + "%").query()));

            // search with tag'name
            List<Tag> tags = tagDao.queryBuilder().where().like(Tag.NAME_FIELD, text + "%").query();

            for (Tag tag : tags) {
                repoForTagPreparedQuery.setArgumentHolderValue(0, tag);
                results.addAll(setTagsForRepo(repositoryDao.query(repoForTagPreparedQuery)));
            }
            // search with category'name
            List<Category> categories = categoryDao.queryBuilder().where().like(Category.NAME_FIELD,text+"%").query();

            for(Category category : categories){
                repoForCategoryOrderByStarPreparedQuery.setArgumentHolderValue(0,category);
                results.addAll(setTagsForRepo(repositoryDao.query(repoForCategoryOrderByStarPreparedQuery)));
            }
            return results;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }

    public List<Repository> getAllReposOrderByDate() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy("createdAt", false).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }

    public List<Repository> getAllReposOrderByStar() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy(Repository.STAR_FIELD, false).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }


    public void createOrUpdateRepository(Repository repository) {
        try {
            repositoryDao.createOrUpdate(repository);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateRepository(Repository repository) {
        try {
            if (repository.getBookmark())
                repository.setBookmarkedAt(Calendar.getInstance().getTime());
            else
                repository.setBookmarkedAt(null);
            repositoryDao.update(repository);

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void deleteRepository(Repository repository) {
        try {
            repositoryDao.delete(repository);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void registerDaoObserver(Dao.DaoObserver observer) {
        repositoryDao.registerObserver(observer);
    }

    public void unregisterDaoObserver(Dao.DaoObserver observer) {
        repositoryDao.unregisterObserver(observer);
    }

    /**
     * Build our repositories for repository objects that match a category
     */


    private PreparedQuery<Tag> makeTagForRepoQuery() throws SQLException {
        QueryBuilder<RTTagRepository, Integer> rtTRQb = rtTagRepositoryDao.queryBuilder();
        rtTRQb.selectColumns(RTTagRepository.TAG_ID_FIELD_NAME);
        SelectArg repoSA = new SelectArg();
        rtTRQb.where().eq(RTTagRepository.REPO_ID_FIELD_NAME, repoSA);

        QueryBuilder<Tag, Integer> tagQb = tagDao.queryBuilder();
        tagQb.where().in(Tag.ID_FIELD, rtTRQb);
        return tagQb.prepare();
    }

    private PreparedQuery<Repository> makeRepoForCategoryOrderByStarQuery() throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy(Repository.STAR_FIELD, false).where().in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
    }

    private PreparedQuery<Repository> makeRepoForTag() {
        try {
            QueryBuilder<RTTagRepository, Integer> rtTRQB = rtTagRepositoryDao.queryBuilder();
            rtTRQB.selectColumns(RTTagRepository.REPO_ID_FIELD_NAME);
            SelectArg tabSA = new SelectArg();
            rtTRQB.where().eq(RTTagRepository.TAG_ID_FIELD_NAME, tabSA);

            QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
            repositoryQb.where().in(Repository.ID_FIELD, rtTRQB);
            return repositoryQb.prepare();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}