package com.example.choijinjoo.wingdroid.source.remote.firebase;

import com.example.choijinjoo.wingdroid.source.remote.api.GithubAPI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by choijinjoo on 2017. 8. 15..
 */

public class RepositoryDataSource {
    private static RepositoryDataSource instance;

    public static RepositoryDataSource getInstance() {
        if (instance == null) {
            instance = new RepositoryDataSource();
        }
        return instance;
    }

    public DatabaseReference repositories(){
        return FirebaseDatabase.getInstance().getReference().child("repositories");
    }
}
