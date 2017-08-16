package com.example.choijinjoo.wingdroid;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class WingDroidApp extends Application {
    private static WingDroidApp instance;

    public static WingDroidApp getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
        Stetho.initializeWithDefaults(this);
    }

}
