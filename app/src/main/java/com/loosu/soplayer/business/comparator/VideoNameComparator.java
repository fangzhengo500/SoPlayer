package com.loosu.soplayer.business.comparator;

import com.loosu.soplayer.domain.VideoEntry;

import java.util.Comparator;

public class VideoNameComparator implements Comparator<VideoEntry> {
    @Override
    public int compare(VideoEntry o1, VideoEntry o2) {
        int result = o1.getDisplayName().compareTo(o2.getDisplayName());
        if (result == 0) {
            result = o1.hashCode() - o2.hashCode();
        }
        return result;
    }
}
