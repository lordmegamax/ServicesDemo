package com.lmmx.ServicesDemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import com.lmmx.ServicesDemo.messenger.MessengerActivity;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {
    private CheckBox cbStopSelf;

    private MyBinderService serviceRef;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            serviceRef = ((MyBinderService.MyBinder) binder).getService();

            serviceRef.doSomething(new MyBinderService.MyInterface() {
                @Override
                public String getMyString(String s) {
                    Toast.makeText(getApplicationContext(), "String is: " + s, Toast.LENGTH_SHORT).show();

                    return null;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceRef = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

        setProgressBarIndeterminate(true);
        setProgressBarIndeterminateVisibility(true);

        Button bStartExpl = (Button) findViewById(R.id.bStartExpl);
        Button bStartImpl = (Button) findViewById(R.id.bStartImpl);
        Button bStopExpl = (Button) findViewById(R.id.bStopExpl);
        Button bStopImpl = (Button) findViewById(R.id.bStopImpl);
        Button bBindService = (Button) findViewById(R.id.bBindService);
        Button bUnbindService = (Button) findViewById(R.id.bUnbindService);

        cbStopSelf = (CheckBox) findViewById(R.id.cbStopSelf);

        bStartExpl.setOnClickListener(this);
        bStartImpl.setOnClickListener(this);
        bStopExpl.setOnClickListener(this);
        bStopImpl.setOnClickListener(this);
        bBindService.setOnClickListener(this);
        bUnbindService.setOnClickListener(this);
    }

    private void explicitStart() {
        Intent intent = new Intent(this, MyExplicitService.class);
        intent.putExtra(MyExplicitService.IS_STOP_SELF_EXTRA, cbStopSelf.isChecked());
        startService(intent);
    }

    private void explicitStop() {
        stopService(new Intent(this, MyExplicitService.class));
    }

    private void implicitStart() {
        Intent intent = new Intent(MyImplicitService.SOME_ACTION);
        intent.putExtra(MyImplicitService.SOME_STUFF_EXTRA, "stuff_string");
        intent.putExtra(MyImplicitService.IS_STOP_SELF_EXTRA, cbStopSelf.isChecked());

        startService(intent);
    }

    private void implicitStop() {
        stopService(new Intent(MyImplicitService.SOME_ACTION));
    }

    public void startForegroundService(View ignored) {
        startService(new Intent(this, MyForegroundService.class));
    }

    public void stopForegroundService(View ignored) {
        stopService(new Intent(this, MyForegroundService.class));
    }

    public void startAsyncActivity(View ignored) {
        startActivity(new Intent(this, AsyncActivity.class));
    }

    public void startIntentService(View ignored) {
        startService(new Intent(this, MyIntentService.class));
    }

    public void sleepInMainThread(View ignored) throws InterruptedException {
        Thread.sleep(3*1000);
    }

    public void openMessengerActivity(View ignored) {
        startActivity(new Intent(this, MessengerActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bStartExpl:
                explicitStart();
                break;
            case R.id.bStartImpl:
                implicitStart();
                break;
            case R.id.bStopExpl:
                explicitStop();
                break;
            case R.id.bStopImpl:
                implicitStop();
                break;
            case R.id.bBindService:
                Intent binderIntent = new Intent(this, MyBinderService.class);
                bindService(binderIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.bUnbindService:
                unbindService(serviceConnection);
                break;
            default:
                break;
        }
    }
}
