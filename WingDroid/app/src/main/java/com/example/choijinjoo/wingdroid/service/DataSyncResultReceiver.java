package com.example.choijinjoo.wingdroid.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DataSyncResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public DataSyncResultReceiver(Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
    }

    public interface Receiver {
         void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}