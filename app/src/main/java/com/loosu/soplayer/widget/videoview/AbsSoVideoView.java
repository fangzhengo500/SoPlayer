package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.loosu.soplayer.utils.IjkMediaPlayerUtil;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.PixelFormatUtil;
import com.loosu.soplayer.widget.videoview.controller.IMediaController;
import com.loosu.soplayer.widget.videoview.listener.IjkMediaPlayerComponentListener;

import java.io.IOException;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public abstract class AbsSoVideoView extends FrameLayout implements IVideoView {
    private static final String TAG = "AbsSoVideoView";

    private PlayerState mState = PlayerState.IDLE;

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
        initPlayer();
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
            } catch (IllegalStateException e) {
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

    private void initPlayer() {
        IMediaPlayer player = getMediaPlayer();
        player.setOnPreparedListener(mPlayerComponentListener);
        player.setOnErrorListener(mPlayerComponentListener);
        player.setOnBufferingUpdateListener(mPlayerComponentListener);
        player.setOnCompletionListener(mPlayerComponentListener);
        player.setOnInfoListener(mPlayerComponentListener);
        player.setOnSeekCompleteListener(mPlayerComponentListener);
        player.setOnTimedTextListener(mPlayerComponentListener);
        player.setOnVideoSizeChangedListener(mPlayerComponentListener);
    }

    protected void iniSurface() {
        if (getSurfaceHolder() != null) {
            getSurfaceHolder().addCallback(mSurfaceCallback);
        }
    }

    protected void onListenedBufferingUpdate(IMediaPlayer mp, int percent) {
    }

    protected void onListenedCompletion(IMediaPlayer mp) {
    }

    protected boolean onListenedError(IMediaPlayer mp, int what, int extra) {
        return false;
    }

    protected boolean onListenedInfo(IMediaPlayer mp, int what, int extra) {
        return false;
    }

    protected void onListenedPrepared(IMediaPlayer mp) {
    }

    protected void onListenedSeekComplete(IMediaPlayer mp) {
    }

    protected void onListenedVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
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

    private final IjkMediaPlayerComponentListener mPlayerComponentListener = new IjkMediaPlayerComponentListener() {

        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            KLog.w(TAG, "percent = " + percent);
            onListenedBufferingUpdate(mp, percent);
        }

        @Override
        public void onCompletion(IMediaPlayer mp) {
            KLog.w(TAG, "");
            onListenedCompletion(mp);
        }

        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            KLog.w(TAG, "what = " + IjkMediaPlayerUtil.errorToString(getContext(), what) + ", extra = " + extra);
            return onListenedError(mp, what, extra);
        }

        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            KLog.w(TAG, "what = " + IjkMediaPlayerUtil.infoToString(getContext(), what) + ", extra = " + extra);
            return onListenedInfo(mp, what, extra);
        }

        @Override
        public void onPrepared(IMediaPlayer mp) {
            KLog.w(TAG, "");
            onListenedPrepared(mp);
        }


        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            KLog.d(TAG, "");
            onListenedSeekComplete(mp);
        }

        @Override
        public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
            KLog.w(TAG, "text = " + text);
        }

        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
            KLog.w(TAG, "width = " + width + ", height = " + height + ", sar_num = " + sar_num + ", sar_den = " + sar_den);
            onListenedVideoSizeChanged(mp, width, height, sar_num, sar_den);
        }
    };
}
