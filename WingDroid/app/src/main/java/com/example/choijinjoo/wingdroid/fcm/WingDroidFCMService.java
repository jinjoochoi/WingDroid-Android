package com.example.choijinjoo.wingdroid.fcm;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.choijinjoo.wingdroid.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by choijinjoo on 2017. 8. 17..
 */

public class WingDroidFCMService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String body = "";
        if (remoteMessage.getNotification() != null) {
             body = remoteMessage.getNotification().getBody();
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher) // 알림 영역에 노출 될 아이콘.
                .setContentTitle(getString(R.string.app_name)) // 알림 영역에 노출 될 타이틀
                .setContentText(body); // Firebase Console 에서 사용자가 전달한 메시지내용

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(0x1001, notificationBuilder.build());
    }
}
