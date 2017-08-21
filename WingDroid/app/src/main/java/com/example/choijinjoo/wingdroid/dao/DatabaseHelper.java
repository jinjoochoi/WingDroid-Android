package com.example.choijinjoo.wingdroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.choijinjoo.wingdroid.model.Bookmark;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.RTCategoryRepository;
import com.example.choijinjoo.wingdroid.model.RTTagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.model.event.Commit;
import com.example.choijinjoo.wingdroid.model.event.Release;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "WingDroid.db";
    private static final int DATABASE_VERSION = 17;

    private List<BaseRepository> repositories = new ArrayList<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, BaseRepository repository) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        repositories.add(repository);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Repository.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Tag.class);
            TableUtils.createTable(connectionSource, RTCategoryRepository.class);
            TableUtils.createTable(connectionSource, RTTagRepository.class);
            TableUtils.createTable(connectionSource, SearchHistory.class);
            TableUtils.createTable(connectionSource, Release.class);
            TableUtils.createTable(connectionSource, Commit.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Bookmark.class);
            TableUtils.createTable(connectionSource, Committer.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Repository.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Tag.class, true);
            TableUtils.dropTable(connectionSource, RTCategoryRepository.class, true);
            TableUtils.dropTable(connectionSource, RTTagRepository.class, true);
            TableUtils.dropTable(connectionSource, SearchHistory.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Release.class, true);
            TableUtils.dropTable(connectionSource, Commit.class, true);
            TableUtils.dropTable(connectionSource, Bookmark.class, true);
            TableUtils.dropTable(connectionSource, Committer.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our RestaurantData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */

    RepositoryDao repoDao;
    CategoryDao categoryDao;
    TagDao tagDao;
    RTCategoryRepositoryDao rtCategoryRepositoryDao;
    RTTagRepositoryDao rtTagRepositoryDao;
    SearchHistoryDao searchHistoryDao;
    ReleaseDao releaseDao;
    CommitDao commitDao;
    BookmarkDao bookmarkDao;
    UserDao userDao;
    CommitterDao commiterDao;

    public RepositoryDao getRepoDao() {
        try {
            if (repoDao == null) {
                repoDao = new RepositoryDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repoDao;
    }

    public CategoryDao getCategoryDao() {
        try {
            if (categoryDao == null) {
                categoryDao = new CategoryDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryDao;
    }

    public TagDao getTagDao() {
        try {
            if (tagDao == null) {
                tagDao = new TagDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagDao;
    }

    public RTTagRepositoryDao getRTTagRepositoryDao() {
        try {
            if (rtTagRepositoryDao == null) {
                rtTagRepositoryDao = new RTTagRepositoryDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rtTagRepositoryDao;
    }

    public RTCategoryRepositoryDao getRTCategoryRepositoryDao() {
        try {
            if (rtCategoryRepositoryDao == null) {
                rtCategoryRepositoryDao = new RTCategoryRepositoryDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rtCategoryRepositoryDao;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        try {
            if (searchHistoryDao == null) {
                searchHistoryDao = new SearchHistoryDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchHistoryDao;
    }


    public CommitDao getCommitDao() {
        try {
            if (commitDao == null) {
                commitDao = new CommitDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commitDao;
    }


    public ReleaseDao getReleaseDao() {
        try {
            if (releaseDao == null) {
                releaseDao = new ReleaseDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return releaseDao;
    }


    public BookmarkDao getBookmarkDao() {
        try {
            if (bookmarkDao == null) {
                bookmarkDao = new BookmarkDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkDao;
    }

    public UserDao getUserDao() {
        try {
            if (userDao == null) {
                userDao = new UserDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDao;
    }

    public CommitterDao getCommiterDao() {
        try {
            if (commiterDao == null) {
                commiterDao = new CommitterDao(getConnectionSource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commiterDao;
    }



    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        repoDao = null;
        tagDao = null;
        categoryDao = null;
        rtCategoryRepositoryDao = null;
        rtTagRepositoryDao = null;
        searchHistoryDao = null;
        releaseDao = null;
        commitDao = null;
        bookmarkDao = null;
        userDao = null;

    }
}