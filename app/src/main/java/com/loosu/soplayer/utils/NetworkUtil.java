package com.loosu.soplayer.utils;

import android.content.Context;
import android.net.TrafficStats;

public class NetworkUtil {
    /**
     * 获取总流量(下行)
     */
    public static long getTotalRxBytes(Context ctx) {
        return TrafficStats.getUidRxBytes(ctx.getApplicationInfo().uid);
    }

    /**
     * 获取总流量(上行)
     */
    public static long getTotalTxBytes(Context ctx) {
        return TrafficStats.getUidTxBytes(ctx.getApplicationInfo().uid);
    }
}
