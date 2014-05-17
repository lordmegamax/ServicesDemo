package com.lmmx.ServicesDemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AsyncActivity extends Activity {
    private ProgressBar pb;
    private TextView tv;
    private String text;
    private static DummyWorker dummyWorker;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        tv = (TextView) findViewById(R.id.tvResult);

        handler = new Handler();
    }

    public void runAsyncTask(View ignored) {
        // Each AsyncTask instance can be executed only once.
        // If you attempt to call execute a second time, an exception will be thrown
        text = "Some dummy text... And once more...Some dummy text... And once more...Some dummy text... " +
                "And once more...Some dummy text... And once more...Some dummy text... And once more...Some dummy " +
                "text... And once more...Some dummy text... And once more...Some dummy text... And once more...Some dummy" +
                " text... And once more...Some dummy text... And once more...Some dummy text... And once more...Some dummy " +
                "text... And once more...Some dummy text... And once more...";
        pb.setMax(text.length());
        dummyWorker = new DummyWorker(this);
        dummyWorker.execute(text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dummyWorker != null)
            dummyWorker.setActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //        dummyWorker.cancel(true);
        dummyWorker.setActivity(null);
    }

    private class DummyWorker extends AsyncTask<String, Integer, String> {
        private AsyncActivity activity;

        private DummyWorker(AsyncActivity activity) {
            this.activity = activity;
        }

        public AsyncActivity getActivity() {
            return activity;
        }

        public void setActivity(AsyncActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            // Moved to a background thread.
            String result = "";
            int myProgress = 0;

            int inputLength = params[0].length();

            // Perform background processing task, update myProgress]
            for (int i = 1; i <= inputLength; i++) {
                //                if(!isCancelled()) {
                myProgress = i;
                result = result + params[0].charAt(inputLength - i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {/*NOP*/}
                publishProgress(myProgress);
               /* } else {
                    return null;
                }*/
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Synchronized to UI thread.
            // Update progress bar, Notification, or other UI elements
            if (activity != null) {
                ProgressBar pb = (ProgressBar) activity.findViewById(R.id.progressBar);
                pb.setProgress(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Synchronized to UI thread.
            // Report results via UI update, Dialog, or notifications
            /*if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                tv.setText(result);
            }*/

            if (activity != null) {
                TextView tv = (TextView) activity.findViewById(R.id.tvResult);
                tv.setText(result);
                // Doing something...
            } else {
                // Ignoring...
            }
        }
    }

    public void startThread(View ignored) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // In the background....

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // In GUI thread....

                        pb.setProgress(60);
                    }
                });
            }
        });

        thread.start();
    }
}

