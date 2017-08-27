package com.example.choijinjoo.wingdroid.fcm;

import android.util.Log;

import com.example.choijinjoo.wingdroid.model.FcmToken;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class WingDroidFCMInstanceIdService extends FirebaseInstanceIdService {
    private final static String TAG = "FCM_ID";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FirebaseInstanceId Refreshed token: " + refreshedToken);
        SharedPreferenceHelper.getInstance().putStringValue(getBaseContext(), SharedPreferenceHelper.Config.FCM_TOKEN, refreshedToken);

        FirebaseDatabase.getInstance().getReference().child("user").child(refreshedToken).setValue(new FcmToken(refreshedToken));
    }
}
