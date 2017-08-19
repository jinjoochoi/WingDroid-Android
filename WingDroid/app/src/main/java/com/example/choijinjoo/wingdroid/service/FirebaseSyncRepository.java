package com.example.choijinjoo.wingdroid.service;

import android.content.Context;

import com.example.choijinjoo.wingdroid.dao.CategoryRepository;
import com.example.choijinjoo.wingdroid.dao.RTCategoryRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RTTagRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.TagRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class FirebaseSyncRepository {

    public FirebaseSyncRepository() {
        init();
    }

    FirebaseArray2 repoFirebaseArray;
    FirebaseArray2 categoryFirebaseArray;
    FirebaseArray2 tagsFirebaseArray;

    RepositoryRepository repositoryRepository;
    CategoryRepository categoryRepository;
    TagRepository tagRepository;
    RTTagRepositoryRepository rtTagRepositoryRepository;
    RTCategoryRepositoryRepository rtCategoryRepository;

    private void init(){
        Query repoQuery = FirebaseDatabase.getInstance().getReference("repositories");
        repoFirebaseArray = new FirebaseArray2(repoQuery);

        Query categoryQuery = FirebaseDatabase.getInstance().getReference("categories");
        categoryFirebaseArray = new FirebaseArray2(categoryQuery);

        Query tagQuery = FirebaseDatabase.getInstance().getReference("tags");
        tagsFirebaseArray = new FirebaseArray2(tagQuery);
    }

    public void start(Context context) {
        repositoryRepository = new RepositoryRepository(context);
        categoryRepository = new CategoryRepository(context);
        tagRepository = new TagRepository(context);
        rtCategoryRepository = new RTCategoryRepositoryRepository(context);
        rtTagRepositoryRepository = new RTTagRepositoryRepository(context);

        repoFirebaseArray.setOnChangedListener(repoListener);
        categoryFirebaseArray.setOnChangedListener(categoryListener);
        tagsFirebaseArray.setOnChangedListener(tagsListener);
    }


    FirebaseArray2.OnChangedListener repoListener = new FirebaseArray2.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            Repository repository = repoFirebaseArray.getItem(index).getValue(Repository.class);
            repository.setId(repoFirebaseArray.getItem(index).getKey());
            switch (type) {
                case ADDED:
                    repositoryRepository.createOrUpdateRepository(repository);
                    // relation table
                    rtCategoryRepository.createOrUpdateRepository(repository);
                    rtTagRepositoryRepository.createOrUpdateRepository(repository);
                    break;
                case REMOVED:
                    repositoryRepository.deleteRepository(repository);
                    break;
                case CHANGED:
                    repositoryRepository.updateRepository(repository);
                    break;
                case MOVED:
                    break;
            }
        }

        @Override
        public void onDataChanged(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    FirebaseArray2.OnChangedListener categoryListener = new FirebaseArray2.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            Category category = categoryFirebaseArray.getItem(index).getValue(Category.class);
            category.setId(categoryFirebaseArray.getItem(index).getKey());
            switch (type) {
                case ADDED:
                    categoryRepository.createOrUpdateCategory(category);
                    break;
                case REMOVED:
                    categoryRepository.deleteCategory(category);
                    break;
                case CHANGED:
                    categoryRepository.updateCategory(category);
                    break;
                case MOVED:
                    break;
            }
        }

        @Override
        public void onDataChanged(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    FirebaseArray2.OnChangedListener tagsListener = new FirebaseArray2.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            Tag tag = tagsFirebaseArray.getItem(index).getValue(Tag.class);
            tag.setId(tagsFirebaseArray.getItem(index).getKey());
            switch (type) {
                case ADDED:
                    tagRepository.createOrUpdateTag(tag);
                    break;
                case REMOVED:
                    tagRepository.deleteTag(tag);
                    break;
                case CHANGED:
                    tagRepository.updateTag(tag);
                    break;
                case MOVED:
                    break;
            }
        }

        @Override
        public void onDataChanged(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
