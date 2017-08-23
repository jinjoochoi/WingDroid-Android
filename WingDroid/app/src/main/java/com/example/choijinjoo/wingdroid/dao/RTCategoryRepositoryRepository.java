package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.RTCategoryRepository;
import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.example.choijinjoo.wingdroid.ui.feed.FeedFragment.ITEM_IN_PAGE;

/**
 * Created by choijinjoo on 2017. 7. 13..
 */

public class RTCategoryRepositoryRepository extends BaseRepository {
    private final String TAG = "RepositoryRepository";
    private RTCategoryRepositoryDao rtCategoryRepositoryDao;
    private RTTagRepositoryDao rtTagRepositoryDao;
    private RepositoryDao repositoryDao;
    private TagDao tagDao;
    private CategoryDao categoryDao;

    private PreparedQuery<Repository> repoForCategoryPreparedQuery = null;
    private PreparedQuery<Tag> tagForRepoPreparedQuery = null;
    private PreparedQuery<Repository> refoForCategoriesPreparedQuery = null;
    private PreparedQuery<Category> categoryForRepoPreparedQuery = null;


    public RTCategoryRepositoryRepository(Context context) {
        super(context);
        rtCategoryRepositoryDao = dbHelper.getRTCategoryRepositoryDao();
        rtTagRepositoryDao = dbHelper.getRTTagRepositoryDao();
        repositoryDao = dbHelper.getRepoDao();
        tagDao = dbHelper.getTagDao();
        categoryDao = dbHelper.getCategoryDao();
    }

    public void createOrUpdateRepository(Repository repository) {
        try {
            rtCategoryRepositoryDao.createOrUpdate(new RTCategoryRepository(repository, repository.getCategory()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public Observable<List<Repository>> getInitialRepoForCategoryOrderByDate(Category category) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeInitialRepoForCategoryOrderByDateQuery();

            repoForCategoryPreparedQuery.setArgumentHolderValue(0, category);
            results.addAll(setTagsForRepo(repositoryDao.query(repoForCategoryPreparedQuery)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getNextRepoForCategoryOrderByDate(Category category,Repository repository) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeNextRepoForCategoryOrderByDateQuery(repository);

            repoForCategoryPreparedQuery.setArgumentHolderValue(0, category);
            results.addAll(setTagsForRepo(repositoryDao.query(repoForCategoryPreparedQuery)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getRepoForCategoryOrderByStar(Category category) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeRepoForCategoryOrderByStarQuery();

            repoForCategoryPreparedQuery.setArgumentHolderValue(0, category);
            results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForCategoryPreparedQuery))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getInitialRepoForCategoryOrderByStar(Category category) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeInitialRepoForCategoryOrderByStarQuery();

            repoForCategoryPreparedQuery.setArgumentHolderValue(0, category);
            results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForCategoryPreparedQuery))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getNextRepoForCategoryOrderByStar(Category category, Repository repository) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryPreparedQuery == null)
                repoForCategoryPreparedQuery = makeNextRepoForCategoryOrderByStarQuery(repository);

            repoForCategoryPreparedQuery.setArgumentHolderValue(0, category);
            results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForCategoryPreparedQuery))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(results);
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

    public Observable<List<Repository>> getBookmarkForCategories(String order_by, List<Category> categories) {
        List<Repository> results = new ArrayList<>();
        try {
            if (refoForCategoriesPreparedQuery == null)
                refoForCategoriesPreparedQuery = makeBookMarkRepoForCategories(order_by, false);
            for (Category category : categories) {
                if (category.getSelected()) {
                    refoForCategoriesPreparedQuery.setArgumentHolderValue(0, category);
                    results.addAll(setTagsForRepo(repositoryDao.query(refoForCategoriesPreparedQuery)));
                }
            }

            refoForCategoriesPreparedQuery.setArgumentHolderValue(0, categories);
            results.addAll(setTagsForRepo(repositoryDao.query(refoForCategoriesPreparedQuery)));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    private List<Repository> setCategoryForRepos(List<Repository> repositories) {
        try {
            if (categoryForRepoPreparedQuery == null)
                categoryForRepoPreparedQuery = makeCategoryForRepoQuery();

            for (Repository repository : repositories) {
                categoryForRepoPreparedQuery.setArgumentHolderValue(0, repository);
                Category category = categoryDao.query(categoryForRepoPreparedQuery).get(0);
                repository.setCategory(category);
            }

            return repositories;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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

    private PreparedQuery<Repository> makeBookMarkRepoForCategories(String order_by, boolean ascending) throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy(order_by, ascending).where().eq("bookmark", true).and().in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
    }

    private PreparedQuery<Repository> makeInitialRepoForCategoryOrderByDateQuery() throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy("createdAt", false).limit((long) ITEM_IN_PAGE).where().in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
    }

    private PreparedQuery<Repository> makeNextRepoForCategoryOrderByDateQuery(Repository last) throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy("createdAt", false).limit((long) ITEM_IN_PAGE).where().lt("createdAt", last.getCreatedAt()).in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
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

    private PreparedQuery<Repository> makeInitialRepoForCategoryOrderByStarQuery() throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy(Repository.STAR_FIELD, false).limit((long) ITEM_IN_PAGE).where().in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
    }

    private PreparedQuery<Repository> makeNextRepoForCategoryOrderByStarQuery(Repository last) throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.REPO_ID_FIELD_NAME);
        SelectArg categorySA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.CATEGORY_ID_FIELD_NAME, categorySA);

        QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
        repositoryQb.orderBy(Repository.STAR_FIELD, false).limit((long) ITEM_IN_PAGE).where().lt(Repository.STAR_FIELD,last.getStar()).in(Repository.ID_FIELD, rtCRQb);
        return repositoryQb.prepare();
    }

    private PreparedQuery<Category> makeCategoryForRepoQuery() throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.CATEGORY_ID_FIELD_NAME);
        SelectArg repoSA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.REPO_ID_FIELD_NAME, repoSA);

        QueryBuilder<Category, Integer> categoryQb = categoryDao.queryBuilder();
        categoryQb.where().in(Category.ID_FIELD, rtCRQb);
        return categoryQb.prepare();
    }

}