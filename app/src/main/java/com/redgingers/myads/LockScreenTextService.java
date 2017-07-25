package com.redgingers.myads;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by rkrde on 25-07-2017.
 */

public class LockScreenTextService extends Service {


    private BroadcastReceiver mReceiver;
    private boolean isShowing = false;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    private WindowManager windowManager;
    private TextView textview;
    WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        //add textview and its properties
        textview = new TextView(this);
        textview.setText("Hello There!");
        textview.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        textview.setTextSize(32f);

        //set parameters for the textview
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

//        params.gravity = Gravity.BOTTOM;

        //Register receiver for determining screen off and if user is present
        mReceiver = new LockScreenStateReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public class LockScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)||(intent.getAction().equals(Intent.ACTION_SCREEN_ON))) {
                //if screen is turn off show the textview
//                if (!isShowing) {
//                Log.d()
//                windowManager.removeViewImmediate(textview);
//                    windowManager.addView(textview, params);
                setView();
//                    isShowing = true;
//                }
            }

            else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                //Handle resuming events if user is present/screen is unlocked remove the textview immediately
//                if (isShowing) {
//                    windowManager.removeViewImmediate(textview);
//                    isShowing = false;
//                }
            }
        }
    }

    @Override
    public void onDestroy() {
        //unregister receiver when the service is destroy
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        //remove view if it is showing and the service is destroy
        if (isShowing) {
            windowManager.removeViewImmediate(textview);
            isShowing = false;
        }
        super.onDestroy();
    }

    private void setView(){
        LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        View mView = mInflater.inflate(R.layout.activity_main, null);

        WindowManager.LayoutParams mLayoutParams =
        new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
/* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
                PixelFormat.RGBA_8888);

        mLayoutParams  = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_INPUT_METHOD |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,// | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        mWindowManager.addView(mView, mLayoutParams);
    }
}
