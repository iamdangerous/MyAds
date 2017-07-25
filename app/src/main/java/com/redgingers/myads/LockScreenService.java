package com.redgingers.myads;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.zip.Inflater;

public class LockScreenService extends Service {

    public String TAG = getClass().getSimpleName();
    BroadcastReceiver brReceiver;
    public LockScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");

        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();

        //Start listening for the Screen On, Screen Off, and Boot completed actions
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        //Set up a receiver to listen for the Intents in this Service
        brReceiver = new LockScreenReceiver();
        registerReceiver(brReceiver, filter);
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        View mView = mInflater.inflate(R.layout.activity_main, null);

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
/* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
                PixelFormat.RGBA_8888);

        mWindowManager.addView(mView, mLayoutParams);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        unregisterReceiver(brReceiver);
        Toast.makeText(this,"Service stopped",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
