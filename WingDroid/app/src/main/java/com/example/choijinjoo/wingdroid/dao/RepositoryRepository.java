package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.util.Log;

import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.RTCategoryRepository;
import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.model.eventbus.RepoBookMarkEvent;
import com.example.choijinjoo.wingdroid.model.eventbus.RepoClickEvent;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

import static com.example.choijinjoo.wingdroid.model.Repository.BOOKMARK_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.DESCRIPTION_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.ID_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.NAME_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.STAR_FIELD;
import static com.example.choijinjoo.wingdroid.ui.feed.FeedFragment.ITEM_IN_PAGE;

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
    private PreparedQuery<Repository> suggestedRepoForTagPreparedQuery = null;
    private PreparedQuery<Category> categoryForRepoPreparedQuery = null;
    private PreparedQuery<Repository> repoForCategoryOrderByStarPreparedQuery = null;


    public static RepositoryRepository instance = null;

    public static RepositoryRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryRepository(context.getApplicationContext());
        }
        return instance;
    }


    public RepositoryRepository(Context context) {
        super(context);
        repositoryDao = dbHelper.getRepoDao();
        rtTagRepositoryDao = dbHelper.getRTTagRepositoryDao();
        rtCategoryRepositoryDao = dbHelper.getRTCategoryRepositoryDao();
        tagDao = dbHelper.getTagDao();
        categoryDao = dbHelper.getCategoryDao();

    }

    public Observable<Repository> getRepositoryById(String repoId) {
        Repository result = null;
        try {
            result = setCategoryForRepo(setTagsForRepo(repositoryDao.queryBuilder().where().eq(Repository.ID_FIELD, repoId).queryForFirst()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(result);
    }


    public Observable<List<Repository>> getRelatedRepo(Repository repository) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForCategoryOrderByStarPreparedQuery == null)
                repoForCategoryOrderByStarPreparedQuery = makeRepoForCategoryOrderByStarQuery();
            repoForCategoryOrderByStarPreparedQuery.setArgumentHolderValue(0, repository.getCategory());
            List<Repository> repos = setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForCategoryOrderByStarPreparedQuery)));
            if (repos.size() > 2)
                results.addAll(repos.subList(0, 3));
            else
                results.addAll(repos);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getRecentRepo() {
        List<Repository> results = new ArrayList<>();
        try {
            List<Repository> repos = setCategoryForRepos(setTagsForRepo(repositoryDao.queryBuilder().orderBy("createdAt", false).limit((long) 3).query()));
            results.addAll(repos);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public void deleteRepository(Repository repository) {
        try {
            repositoryDao.delete(repository);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
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


    public Repository setCategoryForRepo(Repository repository) {
        try {
            if (categoryForRepoPreparedQuery == null)
                categoryForRepoPreparedQuery = makeCategoryForRepoQuery();
            categoryForRepoPreparedQuery.setArgumentHolderValue(0, repository);
            Category category = categoryDao.query(categoryForRepoPreparedQuery).get(0);

            repository.setCategory(category);
            return repository;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return repository;
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

    public Observable<List<Repository>> getSuggestedRepo() {
        List<Repository> results = new ArrayList<>();
        try {
            if (tagForRepoPreparedQuery == null)
                tagForRepoPreparedQuery = makeTagForRepoQuery();

            if (suggestedRepoForTagPreparedQuery == null)
                suggestedRepoForTagPreparedQuery = makeSuggestedRepoForTag();

            // 1. bookmark기반 추천
            List<Repository> bookmarks = repositoryDao.queryBuilder().where().eq(Repository.BOOKMARK_FIELD, true).query();
            if (bookmarks.size() > 0) {
                for (Repository bookmark : bookmarks) {
                    tagForRepoPreparedQuery.setArgumentHolderValue(0, bookmark);
                    List<Tag> tags = tagDao.query(tagForRepoPreparedQuery);
                    for (Tag tag : tags) {
                        suggestedRepoForTagPreparedQuery.setArgumentHolderValue(0, tag);
                        results.addAll(setTagsForRepo(repositoryDao.query(suggestedRepoForTagPreparedQuery)));
                    }
                }
            } else {
                // 2. click기반 추천
                List<Tag> tags = tagDao.queryBuilder().orderBy(Tag.CLICK_FIELD, false).query();
                if (tags.size() >= 3) {
                    for (Tag tag : tags) {
                        suggestedRepoForTagPreparedQuery.setArgumentHolderValue(0, tag);
                        results.addAll(setTagsForRepo(repositoryDao.query(suggestedRepoForTagPreparedQuery)));
                    }
                } else {
                    // 3. star기반 추천
                    results.addAll(repositoryDao.queryBuilder().orderBy(STAR_FIELD, false).where().eq(BOOKMARK_FIELD, false).query());
                }
            }

            return Observable.just(results);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Observable.just(null);
    }


    //TODO : distinct
    public List<Repository> getRepositoryByText(String text) {
        List<Repository> results = new ArrayList<>();
        try {
            if (repoForTagPreparedQuery == null)
                repoForTagPreparedQuery = makeRepoForTag();

            if (repoForCategoryOrderByStarPreparedQuery == null)
                repoForCategoryOrderByStarPreparedQuery = makeRepoForCategoryOrderByStarQuery();

            // search with repo's name, repo's description
            results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.queryBuilder().where().like(DESCRIPTION_FIELD, text + "%").or().like(NAME_FIELD, text + "%").query())));

            // search with tag'name
            List<Tag> tags = tagDao.queryBuilder().where().like(Tag.NAME_FIELD, text + "%").query();

            for (Tag tag : tags) {
                repoForTagPreparedQuery.setArgumentHolderValue(0, tag);
                results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForTagPreparedQuery))));
            }
            // search with category'name
            List<Category> categories = categoryDao.queryBuilder().where().like(Category.NAME_FIELD, text + "%").query();

            for (Category category : categories) {
                repoForCategoryOrderByStarPreparedQuery.setArgumentHolderValue(0, category);
                results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.query(repoForCategoryOrderByStarPreparedQuery))));
            }
            return results;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return results;
    }


    public Observable<List<Repository>> getBookmark() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(repositoryDao.queryBuilder().where().eq(BOOKMARK_FIELD, true).query());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getBookmark(String order_by, boolean desc) {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setCategoryForRepos(setTagsForRepo(repositoryDao.queryBuilder().orderBy(order_by, desc).where().eq(BOOKMARK_FIELD, true).query())));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getInitialReposOrderByDate() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy("createdAt", false).limit((long) ITEM_IN_PAGE).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getNextReposOrderByDate(Repository last) {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy("createdAt", false).limit((long) ITEM_IN_PAGE).where().lt("createdAt", last.getCreatedAt()).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getInitialReposOrderByStar() {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy(Repository.STAR_FIELD, false).limit((long) ITEM_IN_PAGE).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }

    public Observable<List<Repository>> getNextReposOrderByStar(Repository last) {
        List<Repository> results = new ArrayList<>();
        try {
            results.addAll(setTagsForRepo(repositoryDao.queryBuilder().orderBy(Repository.STAR_FIELD, false).limit((long) ITEM_IN_PAGE).where().lt(Repository.STAR_FIELD, last.getStar()).query()));
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return Observable.just(results);
    }


    public void createOrUpdateRepository(Repository repository) {
        try {
            Repository prev = repositoryDao.queryBuilder().where().eq(ID_FIELD, repository.getId()).queryForFirst();
            if (prev != null) {
                Log.d(TAG, "prev's bookmark : " + prev.getBookmark());
                repository.setBookmarkedAt(repository.getBookmarkedAt());
                repository.setBookmark(repository.getBookmark());
                repository.setClicks(repository.getClicks());
            }
            repositoryDao.createOrUpdate(repository);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateRepository(Repository repository) {
        try {
            repositoryDao.update(repository);

        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addClick(Repository repository) {
        try {
            repositoryDao.update(repository);
            EventBus.getDefault().post(new RepoClickEvent());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addBookmark(Repository repository) {
        try {
            if (repository.getBookmark())
                repository.setBookmarkedAt(Calendar.getInstance().getTime());
            else
                repository.setBookmarkedAt(null);
            repositoryDao.update(repository);

            EventBus.getDefault().post(new RepoBookMarkEvent());
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    /**
     * Build our repositories for repository objects that match a category
     */

    private PreparedQuery<Category> makeCategoryForRepoQuery() throws SQLException {
        QueryBuilder<RTCategoryRepository, Integer> rtCRQb = rtCategoryRepositoryDao.queryBuilder();
        rtCRQb.selectColumns(RTCategoryRepository.CATEGORY_ID_FIELD_NAME);
        SelectArg repoSA = new SelectArg();
        rtCRQb.where().eq(RTCategoryRepository.REPO_ID_FIELD_NAME, repoSA);

        QueryBuilder<Category, Integer> categoryQb = categoryDao.queryBuilder();
        categoryQb.where().in(Category.ID_FIELD, rtCRQb);
        return categoryQb.prepare();
    }

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

    private PreparedQuery<Repository> makeSuggestedRepoForTag() {
        try {
            QueryBuilder<RTTagRepository, Integer> rtTRQB = rtTagRepositoryDao.queryBuilder();
            rtTRQB.selectColumns(RTTagRepository.REPO_ID_FIELD_NAME);
            SelectArg tabSA = new SelectArg();
            rtTRQB.where().eq(RTTagRepository.TAG_ID_FIELD_NAME, tabSA);

            QueryBuilder<Repository, Integer> repositoryQb = repositoryDao.queryBuilder();
            repositoryQb.where().eq("bookmark", false).and().in(Repository.ID_FIELD, rtTRQB);
            return repositoryQb.prepare();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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