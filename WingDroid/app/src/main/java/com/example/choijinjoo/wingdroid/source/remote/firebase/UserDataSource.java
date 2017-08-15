package com.example.choijinjoo.wingdroid.source.remote.firebase;

import com.example.choijinjoo.wingdroid.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DatabaseReference users() {
        return FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void addUser(User user) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getId())
                .setValue(user);
    }
}
