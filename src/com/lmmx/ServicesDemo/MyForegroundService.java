package com.lmmx.ServicesDemo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class MyForegroundService extends Service {
    private static final int NOTIFICATION_ID = 3;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent actIntent = new Intent(this, MyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, actIntent, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendingIntent)
               .setSmallIcon(android.R.drawable.ic_menu_info_details)
               .setContentTitle("Service is running in foreground...")
               .setTicker("Wow! It's working!")
               .setWhen(System.currentTimeMillis())
               .setSubText("Why are you so serious?")
               .setOngoing(true);

        Notification notification;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContentText(":)");
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        startForeground(NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
