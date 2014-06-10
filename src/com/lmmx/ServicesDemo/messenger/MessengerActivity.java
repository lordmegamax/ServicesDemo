package com.lmmx.ServicesDemo.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerActivity extends Activity {
    public static final String EXTRA_MESSENGER = "messenger";
    private static final String TAG = "MessengerActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long captureTime = System.currentTimeMillis();
            Log.d(TAG, "Capture message, time: " + captureTime);
            Log.d(TAG, "handleMessage(): " + msg);

            Log.d(TAG, "Diff between sending and receiving the message is: " + (captureTime - (Long) msg.obj) + "ms.");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Messenger messenger = new Messenger(handler);
        runServiceWithMessenger(messenger);
    }

    private void runServiceWithMessenger(Messenger messenger) {
        Intent intent = new Intent(this, MessengerService.class);
        intent.putExtra(EXTRA_MESSENGER, messenger);

        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MessengerService.class));
    }
}