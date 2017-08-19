package com.example.choijinjoo.wingdroid.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.choijinjoo.wingdroid.tools.IntentService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class FirebaseDataSyncService extends IntentService {

    PublishSubject<Boolean> loadedCategory = PublishSubject.create();
    PublishSubject<Boolean> loadedRepository = PublishSubject.create();
    PublishSubject<Boolean> loadedTag = PublishSubject.create();
    PublishSubject<Boolean> needUpdate = PublishSubject.create();

    Disposable disposable;
    Disposable updatedisposable;

    @Override
    protected void onHandleIntent(Intent intent) {

        disposable = Observable.combineLatest(loadedCategory, loadedRepository, loadedTag,
                (r1, r2, r3) -> r1 && r2 && r3)
                .subscribe(result -> sendBRToSplashActv());

        updatedisposable = needUpdate
                .filter(needUpdate -> !needUpdate)
                .subscribe(it -> sendBRToSplashActv());

        FirebaseSyncRepository firebaseSyncRepository = new FirebaseSyncRepository(this, loadedCategory, loadedRepository, loadedTag, needUpdate);
        firebaseSyncRepository.start();
    }

    private void sendBRToSplashActv(){
        Intent receiverIntent = new Intent("initial-load");
        receiverIntent.putExtra("result", RESULT_OK);
        LocalBroadcastManager.getInstance(this).sendBroadcast(receiverIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        updatedisposable.dispose();
    }
}
