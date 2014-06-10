package com.lmmx.ServicesDemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");

        final Messenger messenger = intent.getParcelableExtra(MessengerActivity.EXTRA_MESSENGER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Started a new thread.");
                try {
                    int SECONDS = 1000;
                    Thread.sleep(10 * SECONDS);

                    Message message = Message.obtain();
                    message.what = 123;
                    message.arg1 = 456;
                    message.arg2 = 789;

                    long time = System.currentTimeMillis();
                    message.obj = time;
                    Log.d(TAG, "Sending message from the service, time: " + time);
                    messenger.send(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");
    }
}
