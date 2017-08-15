package com.example.choijinjoo.wingdroid.source.remote.firebase;

import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public interface RepositoryListener {
        void added(Repository repository);

        void empty();
    }

    public void getRepositoryById(String id, RepositoryListener listener) {
        FirebaseDatabase.getInstance().getReference("repositories").child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null)
                            listener.empty();
                        else
                            listener.added(dataSnapshot.getValue(Repository.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public DatabaseReference repositories() {
        return FirebaseDatabase.getInstance().getReference("repositories");
    }

    public Query repositoriesByCategory(Category category) {
        return FirebaseDatabase.getInstance().getReference("categories").child(category.getId()).child("repositories");
    }
}
