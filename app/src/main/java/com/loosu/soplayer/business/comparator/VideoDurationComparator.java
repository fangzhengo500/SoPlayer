package com.loosu.soplayer.business.comparator;

import com.loosu.soplayer.domain.VideoEntry;

import java.util.Comparator;

public class VideoDurationComparator implements Comparator<VideoEntry> {
    @Override
    public int compare(VideoEntry o1, VideoEntry o2) {
        long result = o1.getDuration() - o2.getDuration();
        if (result == 0) {
            result = o1.hashCode() - o2.hashCode();
        }
        return (int) result;
    }
}
