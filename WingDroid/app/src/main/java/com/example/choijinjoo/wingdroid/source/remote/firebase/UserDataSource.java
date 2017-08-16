package com.example.choijinjoo.wingdroid.source.remote.firebase;

import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.model.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by choijinjoo on 2017. 8. 15..
 */

public class UserDataSource {
    private static UserDataSource instance;

    public static UserDataSource getInstance() {
        if (instance == null) {
            instance = new UserDataSource();
        }
        return instance;
    }

    public void addBookmark(String userId, String repositoryId) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .child("bookmark")
                .child(repositoryId)
                .setValue(repositoryId);
    }

    public void deleteBookmark(String userId, String repositoryId) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .child("bookmark")
                .child(repositoryId)
                .removeValue();
    }

    public Query getBookmark(String userId) {
        return FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .child("bookmark");
    }

    public Query getSearchHistories(String userId) {
        return FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .child("searchHistories")
                .orderByChild("insertedAt/time");
    }

    public void deleteSearchHistory(String userId, String historyId) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child("searchHistories")
                .child(historyId)
                .removeValue();
    }

    public void addUser(User user) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getId())
                .setValue(user);
    }

    public void addSearchHistory(String userId, SearchHistory searchHistory) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .child("searchHistories")
                .child(String.valueOf(searchHistory.getInsertedAt().getTime()))
                .setValue(searchHistory);
    }
}
