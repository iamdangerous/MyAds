package com.redgingers.myads.windowManager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

import com.redgingers.myads.utils.Utils;

/**
 * Created by maninder on 25/7/17.
 */

public class WindowServiceProvider {

    private
    static WindowManager sWindowManager = null;

    public static WindowManager get(Context mContext) {

        if (sWindowManager == null) {
            sWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        }
        return sWindowManager;
    }

    public static WindowManager.LayoutParams getParams(Context context) {

        WindowManager.LayoutParams params = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,

                    // send key events to back windows
                    PixelFormat.TRANSLUCENT);
        } else {

            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    // send key events to back windows
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.RIGHT | Gravity.BOTTOM | Gravity.END;
        params.y = Utils.getStatusBarHeight(context);
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                | WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED;

        params.windowAnimations = android.R.style.Animation_Activity;

        return params;
    }
}