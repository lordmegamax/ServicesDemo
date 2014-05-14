package com.lmmx.ServicesDemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("Some name");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // This handler occurs on a background thread.

        Log.d(TAG, "onHandleIntent");

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {/*NOP*/}

        Log.d(TAG, "End of onHandleIntent method execution...");
    }
}
