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
