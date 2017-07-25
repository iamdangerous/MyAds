package com.redgingers.myads.utils;

import android.content.Context;

/**
 * Created by maninder on 25/7/17.
 */

public class Utils {


    public static int getStatusBarHeight(Context mContext) {
        int statusBarHeight = 0;

        int resourceId = mContext.getResources().getIdentifier(
                "status_bar_height", "dimen", "android"
        );

        if (resourceId > 0) {
            statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }
}
