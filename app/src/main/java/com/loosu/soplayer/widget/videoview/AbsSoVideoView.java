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
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;
import com.loosu.soplayer.widget.videoview.interfaces.IVideoView;
import com.loosu.soplayer.widget.videoview.listener.IjkMediaPlayerComponentListener;

import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public abstract class AbsSoVideoView extends FrameLayout implements IVideoView, IMediaController {
    private static final String TAG = "AbsSoVideoView";

    private State mState = State.IDLE;

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

    @Override
    public State getState() {
        return mState;
    }

    private void setState(State state) {
        KLog.e(TAG, "state = " + state);
        mState = state;
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        final IMediaPlayer player = getMediaPlayer();
        try {
            player.reset();
            player.setDisplay(getSurfaceHolder());
            player.setDataSource(context, uri);

            setState(State.INITIALIZED);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public void setDataSource(String path) {
        KLog.d(TAG, "path = " + path);
        final IMediaPlayer player = getMediaPlayer();
        try {
            player.reset();
            player.setDisplay(getSurfaceHolder());
            player.setDataSource(path);

            setState(State.INITIALIZED);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public String getDataSource() {
        return getMediaPlayer() == null ? null : getMediaPlayer().getDataSource();
    }

    @Override
    public void start() {
        try {
            getMediaPlayer().prepareAsync();
            setState(State.PREPARING);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public void stop() {
        try {
            getMediaPlayer().stop();
            setState(State.STOPPED);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public void pause() {
        try {
            getMediaPlayer().pause();
            setState(State.PAUSED);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public void resume() {
        try {
            getMediaPlayer().start();
            if (getState() == State.PAUSED || getState() == State.PLAYBACK_COMPLETED) {
                setState(State.STARTED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
        }
    }

    @Override
    public void release() {
        try {
            getMediaPlayer().release();
            setState(State.END);
        } catch (Exception e) {
            e.printStackTrace();
            setState(State.ERROR);
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

    private void bufferingUpdate(IMediaPlayer mp, int percent) {
        onBufferingUpdate(mp, percent);
    }

    private void completion(IMediaPlayer mp) {
        setState(State.PLAYBACK_COMPLETED);
        onCompletion(mp);
    }

    private boolean error(IMediaPlayer mp, int what, int extra) {
        setState(State.ERROR);
        return onError(mp);
    }

    private boolean info(IMediaPlayer mp, int what, int extra) {
        if (mp.isPlaying() && (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START || what == IMediaPlayer.MEDIA_INFO_VIDEO_SEEK_RENDERING_START)) {
            setState(State.STARTED);
            onStarted(mp);
        }
        return false;
    }

    private void prepared(IMediaPlayer mp) {
        setState(State.PREPARED);
        onPrepared(mp);
    }

    private void seekComplete(IMediaPlayer mp) {
        onSeekComplete(mp);
    }

    private void videoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        onVideoSizeChanged(mp, width, height, sar_num, sar_den);
    }

    protected void onBufferingUpdate(IMediaPlayer mp, int percent) {
    }

    protected void onCompletion(IMediaPlayer mp) {
    }

    protected boolean onError(IMediaPlayer mp) {
        return false;
    }

    protected void onPrepared(IMediaPlayer mp) {
    }

    protected void onStarted(IMediaPlayer mp) {
    }

    protected void onSeekComplete(IMediaPlayer mp) {
    }

    protected void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {

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
            KLog.d(TAG, String.format(Locale.US, "holder = %s, format = %s, width = %d, height = %d", holder, PixelFormatUtil.formatToString(format), width, height));
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.d(TAG, String.format(Locale.US, "holder = %s", holder));
            if (getMediaPlayer() != null) {
                getMediaPlayer().stop();
            }
        }
    };

    private final IjkMediaPlayerComponentListener mPlayerComponentListener = new IjkMediaPlayerComponentListener() {

        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            KLog.d(TAG, "percent = " + percent);
            bufferingUpdate(mp, percent);
        }

        @Override
        public void onCompletion(IMediaPlayer mp) {
            KLog.w(TAG, "");
            completion(mp);
        }

        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            KLog.w(TAG, "what = " + IjkMediaPlayerUtil.errorToString(getContext(), what) + ", extra = " + extra);
            return error(mp, what, extra);
        }

        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            KLog.w(TAG, "what = " + IjkMediaPlayerUtil.infoToString(getContext(), what) + ", extra = " + extra);
            return info(mp, what, extra);
        }

        @Override
        public void onPrepared(IMediaPlayer mp) {
            KLog.w(TAG, "");
            prepared(mp);
        }


        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            KLog.w(TAG, "");
            seekComplete(mp);
        }

        @Override
        public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
            KLog.w(TAG, "text = " + text);
        }

        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
            KLog.w(TAG, "width = " + width + ", height = " + height + ", sar_num = " + sar_num + ", sar_den = " + sar_den);
            videoSizeChanged(mp, width, height, sar_num, sar_den);
        }
    };
}
