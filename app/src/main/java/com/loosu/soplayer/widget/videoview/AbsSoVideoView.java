package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.PixelFormatUtil;

import java.io.IOException;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public abstract class AbsSoVideoView extends FrameLayout implements IVideoView {
    private static final String TAG = "AbsSoVideoView";

    private PlayerState mState;

    public AbsSoVideoView(@NonNull Context context) {
        super(context);
    }

    public AbsSoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsSoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        KLog.d(TAG, "");
        super.onAttachedToWindow();
        iniSurface();
    }

    @Override
    protected void onDetachedFromWindow() {
        KLog.d(TAG, "");
        super.onDetachedFromWindow();
    }

    protected abstract IMediaPlayer getMediaPlayer();

    protected abstract SurfaceHolder getSurfaceHolder();

    public PlayerState getState() {
        return mState;
    }

    private void setState(PlayerState state) {
        mState = state;
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException {
        final IMediaPlayer player = getMediaPlayer();
        if (player != null) {
            player.reset();
            player.setDisplay(getSurfaceHolder());
            player.setDataSource(context, uri);
        }
    }

    @Override
    public void setDataSource(String path) throws IOException {
        KLog.d(TAG, "path = " + path);
        final IMediaPlayer player = getMediaPlayer();
        if (player != null) {
            player.reset();
            player.setDisplay(getSurfaceHolder());
            player.setDataSource(path);
        }
    }

    @Override
    public String getDataSource() {
        return getMediaPlayer() == null ? null : getMediaPlayer().getDataSource();
    }

    @Override
    public void start() {
        if (getMediaPlayer() != null) {
            try {
                getMediaPlayer().prepareAsync();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resume() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().start();
        }
    }

    @Override
    public void pause() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().pause();
        }
    }

    @Override
    public void release() {
        if (getMediaPlayer() != null) {
            getMediaPlayer().release();
        }
    }

    protected void iniSurface() {
        if (getSurfaceHolder() != null) {
            getSurfaceHolder().addCallback(mSurfaceCallback);
        }
    }

    private final SurfaceHolder.Callback2 mSurfaceCallback = new SurfaceHolder.Callback2() {
        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {
            KLog.d(TAG, String.format(Locale.US, "holder = %s", holder));
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            KLog.d(TAG, String.format(Locale.US, "holder = %s", holder));
            if (getMediaPlayer() != null) {
                getMediaPlayer().setDisplay(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            KLog.d(TAG, String.format(Locale.US, "holder = %s, format = %s, width = %d, height = %d",
                    holder, PixelFormatUtil.formatToString(format), width, height));
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.d(TAG, String.format(Locale.US, "holder = %s", holder));
            if (getMediaPlayer() != null) {
                getMediaPlayer().release();
            }
        }
    };
}
