package com.loosu.soplayer.business.comparator;

import com.loosu.soplayer.domain.VideoEntry;

import java.util.Comparator;

public class VideoSizeComparator implements Comparator<VideoEntry> {
    @Override
    public int compare(VideoEntry o1, VideoEntry o2) {
        long result = o1.getSize() - o2.getSize();
        if (result > 0) {
            result = 1;
        } else if (result < 0) {
            result = -1;
        } else {
            result = o1.hashCode() - o2.hashCode();
        }
        return (int) result;
    }
}
