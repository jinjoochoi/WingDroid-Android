package com.example.choijinjoo.wingdroid;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;

import com.example.choijinjoo.wingdroid.service.BookmarkEventSyncService;
import com.example.choijinjoo.wingdroid.service.FirebaseDataSyncService;
import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class WingDroidApp extends Application {
    private static WingDroidApp instance;
    public static final int JOB_ID = 1001;

    public static WingDroidApp getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
        Stetho.initializeWithDefaults(this);

        startDataSyncService();
        startBookmarkEventSyncService();
    }

    private void startBookmarkEventSyncService(){
        JobInfo job = new JobInfo.Builder(
                JOB_ID, new ComponentName(this, BookmarkEventSyncService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(12 * DateUtils.MINUTE_IN_MILLIS)
                .build();

        JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(job);
    }

    private void startDataSyncService(){
        Intent intent = new Intent(this, FirebaseDataSyncService.class);
        startService(intent);
    }

}
