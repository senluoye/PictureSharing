package com.wkh.picturesharingapplication.utils;

import android.content.Context;
import android.view.WindowManager;

public class DisplayUtils {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getStatusBarSize(Context context) {
        int statusBarSize = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            //根据资源ID获取响应的尺寸值
            statusBarSize = context.getResources().getDimensionPixelSize(resourceId);

        return statusBarSize;
    }
}
