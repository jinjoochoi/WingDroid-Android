package com.example.choijinjoo.wingdroid.service;

import android.content.Intent;

import com.example.choijinjoo.wingdroid.tools.IntentService;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class FirebaseDataSyncService extends IntentService {


    @Override
    protected void onHandleIntent(Intent intent) {
        FirebaseSyncRepository firebaseSyncRepository = new FirebaseSyncRepository();
        firebaseSyncRepository.start(this);
    }

}
