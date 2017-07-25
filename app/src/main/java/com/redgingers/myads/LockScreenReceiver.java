
package com.redgingers.myads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_SCREEN_ON;

public class LockScreenReceiver extends BroadcastReceiver {

    String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        switch (action) {
            case ACTION_SCREEN_ON: {
                Log.d(TAG, "ACTION_SCREEN_ON");
                sampleView(context);
//                showMainActivity(context);
            }
            break;
            case ACTION_SCREEN_OFF: {
                Log.d(TAG, "ACTION_SCREEN_OFF");
                sampleView(context);
            }
            break;
            case ACTION_BOOT_COMPLETED: {
                Log.d(TAG, "ACTION_BOOT_COMPLETED");
                showMainActivity(context);
            }
            break;
        }
    }

    private void showMainActivity(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }



    private void sampleView(Context context){
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        View mView = mInflater.inflate(R.layout.activity_main, null);

//        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
///* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
//                PixelFormat.RGBA_8888);

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 100, 100,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
/* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
                PixelFormat.RGBA_8888);

        mWindowManager.addView(mView, mLayoutParams);
    }
}
