package com.example.choijinjoo.wingdroid.source.remote.firebase;

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

    public void addUser(User user) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getId())
                .setValue(user);
    }
}
