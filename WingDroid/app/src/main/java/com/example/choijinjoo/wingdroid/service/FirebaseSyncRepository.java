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
import com.example.choijinjoo.wingdroid.model.UpdatedAt;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import io.reactivex.subjects.PublishSubject;

import static com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper.Config.LOAD_UPDATEDAT;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class
FirebaseSyncRepository {
    Context context;

    PublishSubject<Boolean> loadedCategoryPS;
    PublishSubject<Boolean> loadedRepositoryPS;
    PublishSubject<Boolean> loadedTagPS;
    PublishSubject<Boolean> needUpdatePS;

    FirebaseArray2 repoFirebaseArray;
    FirebaseArray2 categoryFirebaseArray;
    FirebaseArray2 tagsFirebaseArray;
    FirebaseArray2 updatedAtFirebaseArray;

    RepositoryRepository repositoryRepository;
    CategoryRepository categoryRepository;
    TagRepository tagRepository;
    RTTagRepositoryRepository rtTagRepositoryRepository;
    RTCategoryRepositoryRepository rtCategoryRepository;

    boolean initial_load_category = false;
    boolean initial_load_repository = false;
    boolean initial_load_tag = false;

    boolean need_update = false;

    public FirebaseSyncRepository(Context context, PublishSubject<Boolean> loadedCategory, PublishSubject<Boolean> loadedRepository, PublishSubject<Boolean> loadedTag, PublishSubject<Boolean> needUpdate) {
        this.context = context;
        this.loadedCategoryPS = loadedCategory;
        this.loadedRepositoryPS = loadedRepository;
        this.loadedTagPS = loadedTag;
        this.needUpdatePS = needUpdate;
        init(context);
    }

    Query updatedAtQuery;
    Query repoQuery;
    Query categoryQuery;
    Query tagQuery;

    private void init(Context context) {
        updatedAtQuery = FirebaseDatabase.getInstance().getReference("UpdatedAt");
        repoQuery = FirebaseDatabase.getInstance().getReference("repositories");
        categoryQuery = FirebaseDatabase.getInstance().getReference("categories");
        tagQuery = FirebaseDatabase.getInstance().getReference("tags");

        categoryRepository = new CategoryRepository(context);
        repositoryRepository = new RepositoryRepository(context);
        tagRepository = new TagRepository(context);
        rtCategoryRepository = new RTCategoryRepositoryRepository(context);
        rtTagRepositoryRepository = new RTTagRepositoryRepository(context);

    }

    public void start() {
        updatedAtFirebaseArray = new FirebaseArray2(updatedAtQuery);
        updatedAtFirebaseArray.setOnChangedListener(updatedAtListener);
    }


    FirebaseArray2.OnChangedListener repoListener = new FirebaseArray2.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            Repository repository = repoFirebaseArray.getItem(index).getValue(Repository.class);
            repository.setId(repoFirebaseArray.getItem(index).getKey());
            switch (type) {
                case ADDED:
                    rtCategoryRepository.createOrUpdateRepository(repository);
                    rtTagRepositoryRepository.createOrUpdateRepository(repository);
                    repositoryRepository.createOrUpdateRepository(repository);
                    // relation table
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
            if (need_update && !initial_load_repository) {
                loadedRepositoryPS.onNext(true);
                initial_load_repository = true;
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (need_update && !initial_load_repository) {
                loadedRepositoryPS.onNext(false);
                initial_load_repository = true;
            }
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
            if (need_update && !initial_load_category) {
                loadedCategoryPS.onNext(true);
                initial_load_category = true;
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (need_update && !initial_load_category) {
                loadedCategoryPS.onNext(false);
                initial_load_category = true;
            }
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
            if (need_update && !initial_load_tag) {
                loadedTagPS.onNext(true);
                initial_load_tag = true;
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            if (need_update && !initial_load_tag) {
                loadedTagPS.onNext(false);
                initial_load_tag = true;
            }
        }
    };

    FirebaseArray2.OnChangedListener updatedAtListener = new FirebaseArray2.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            switch (type) {
                case ADDED:
                    UpdatedAt updatedAt = updatedAtFirebaseArray.getItem(index).getValue(UpdatedAt.class);
                    Long pre_updatedAt = SharedPreferenceHelper.getInstance().getLongValue(context, LOAD_UPDATEDAT);
                    need_update = !updatedAt.getUpdatedAt().equals(pre_updatedAt);
                    if (!need_update) {
                        needUpdatePS.onNext(need_update);
                    }
                    //FIXME : Load가 다 된 다음에
                    SharedPreferenceHelper.getInstance().putLongValue(context,LOAD_UPDATEDAT,updatedAt.getUpdatedAt());
                    categoryFirebaseArray = new FirebaseArray2(categoryQuery);
                    tagsFirebaseArray = new FirebaseArray2(tagQuery);
                    repoFirebaseArray = new FirebaseArray2(repoQuery);

                    repoFirebaseArray.setOnChangedListener(repoListener);
                    categoryFirebaseArray.setOnChangedListener(categoryListener);
                    tagsFirebaseArray.setOnChangedListener(tagsListener);
                    break;
                case CHANGED:
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
