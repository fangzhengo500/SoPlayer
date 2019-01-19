package com.loosu.soplayer.widget.videoview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.loosu.soplayer.utils.KLog;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class FullScreenSoVideoView extends SoVideoView {
    private static final String TAG = "FullScreenSoVideoView";

    private long mOffset = -1;

    public FullScreenSoVideoView(@NonNull Context context, long offset) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        mOffset = offset;

        showVideo();
    }

    @Override
    public void setDataSource(String path) {
        super.setDataSource(path);
        start();
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        super.setDataSource(context, uri);
        start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KLog.e(TAG, "keyCode = " + keyCode + ", event = " + event);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ViewParent parent = getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this);
            }
            if (getContext()instanceof Activity) {
                ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPrepared(IMediaPlayer mp) {
        super.onPrepared(mp);
        if (mOffset != -1) {
            mp.seekTo(mOffset);
        }
    }
}
