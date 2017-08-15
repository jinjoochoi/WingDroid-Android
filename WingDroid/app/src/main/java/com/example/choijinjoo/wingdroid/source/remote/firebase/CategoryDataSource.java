package com.example.choijinjoo.wingdroid.source.remote.firebase;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by choijinjoo on 2017. 8. 15..
 */

public class CategoryDataSource {
    private static CategoryDataSource instance;

    public static CategoryDataSource getInstance() {
        if (instance == null) {
            instance = new CategoryDataSource();
        }
        return instance;
    }

    public Query categories() {
        return FirebaseDatabase.getInstance().getReference("categories").orderByChild("name");
    }
}
