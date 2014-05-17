package com.lmmx.ServicesDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyExplicitService extends Service {
    public static final String IS_STOP_SELF_EXTRA = "is_stop_self_extra";
    public final String TAG = getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        startBackgroundTask(intent, startId);

        Log.d(TAG, this.toString());

        // START_FLAG_RETRY
        // START_FLAG_REDELIVERY

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void startBackgroundTask(Intent intent, int startId) {

    }
}
