package com.lmmx.ServicesDemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncActivity extends Activity {
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void runAsyncTask(View ignored) {
        // Each AsyncTask instance can be executed only once.
        // If you attempt to call execute a second time, an exception will be thrown
        String text = "Some dummy text... And once more...";
        pb.setMax(text.length());
        new DummyWorker().execute(text);
    }

    private class DummyWorker extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // Moved to a background thread.
            String result = "";
            int myProgress = 0;

            int inputLength = params[0].length();

            // Perform background processing task, update myProgress]
            for (int i = 1; i <= inputLength; i++) {
                myProgress = i;
                result = result + params[0].charAt(inputLength - i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {/*NOP*/}
                publishProgress(myProgress);
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Synchronized to UI thread.
            // Update progress bar, Notification, or other UI elements
            pb.setProgress(progress[0]);

            setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // Synchronized to UI thread.
            // Report results via UI update, Dialog, or notifications
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}

