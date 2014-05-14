package com.lmmx.ServicesDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyImplicitService extends Service {
    public static final String SOME_STUFF_EXTRA = "some_stuff_extra";
    public static final String SOME_ACTION = "com.lmmx.SOME_ACTION";
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

        if (intent != null) {
            boolean isStopSelf = intent.getBooleanExtra(IS_STOP_SELF_EXTRA, false);
            if (isStopSelf) {
                Log.d(TAG, "stopSelf");
                stopSelf(startId);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
