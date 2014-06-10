package com.lmmx.ServicesDemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBinderService extends Service {
    public static final String IS_STOP_SELF_EXTRA = "is_stop_self_extra";
    public final String TAG = getClass().getSimpleName();

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        MyBinderService getService() {
            return MyBinderService.this;
        }
    }

    interface MyInterface {
        public String getMyString(String s);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return Service.START_STICKY;
    }

    public void doSomething(MyInterface myInterface ) {
        String s = " Hello world ";
        myInterface.getMyString(s);
    }
}
