package com.loosu.soplayer;

import com.loosu.soplayer.utils.TimeUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit avi, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void timeFormat() {
        String s = TimeUtil.formatDuration(Integer.MAX_VALUE);
        String s1 = TimeUtil.formatDuration(Integer.MIN_VALUE);
    }
}