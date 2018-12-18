package com.loosu.soplayer.utils;

import android.content.Context;
import android.content.res.Resources;

public class SystemUiUtil {
    private static final String PACKAGE = "android";
    private static final String TYPE_DIMEN = "dimen";

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", TYPE_DIMEN, PACKAGE);
        return resources.getDimensionPixelSize(resourceId);
    }

    private int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", TYPE_DIMEN, PACKAGE);
        return resources.getDimensionPixelSize(resourceId);
    }
}
