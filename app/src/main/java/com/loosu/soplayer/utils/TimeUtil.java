package com.loosu.soplayer.utils;

public class TimeUtil {
    public static String formatDuration(long duration) {
        int seconds = (int) (duration / 1000 % 60);
        int minutes = (int) (duration / 1000 / 60 % 60);
        int hours = (int) (duration / 1000 / 60 / 60 % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
