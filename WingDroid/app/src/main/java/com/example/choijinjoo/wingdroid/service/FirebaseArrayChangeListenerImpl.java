package com.example.choijinjoo.wingdroid.service;

import com.example.choijinjoo.wingdroid.tools.FirebaseArray;
import com.google.firebase.database.DatabaseError;

/**
 * Created by choijinjoo on 2017. 8. 18..
 */

public class FirebaseArrayChangeListenerImpl<T,RP> implements FirebaseArray.OnChangedListener {
    @Override
    public void onChildChanged(EventType type, int index, int oldIndex) {
//        T repository = repoFirebaseArray.getItem(index).getValue(Repository.class);
//        repository.setId(repoFirebaseArray.getItem(index).getKey());
//        switch (type) {
//            case ADDED:
//                repositoryRepository.createOrUpdateRepository(repository);
//                break;
//            case REMOVED:
//                repositoryRepository.deleteRepository(repository);
//                break;
//            case CHANGED:
//                repositoryRepository.updateRepository(repository);
//                break;
//            case MOVED:
//                break;
//        }
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
