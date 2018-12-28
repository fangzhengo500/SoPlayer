package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class SoVideoView extends FrameLayout implements IVideoView {
    private static final String TAG = "SoVideoView";

    private IjkMediaPlayer mPlayer;
    private AutoFixSurfaceView mSurfaceView;

    public SoVideoView(@NonNull Context context) {
        this(context, null, 0);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_so_video, this, true);
        mSurfaceView = findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(mSurfaceHolderCallback);

        mPlayer = new IjkMediaPlayer();
        mPlayer.setOnPreparedListener(mIjkMediaPlayerListener);
        mPlayer.setOnErrorListener(mIjkMediaPlayerListener);
        mPlayer.setOnBufferingUpdateListener(mIjkMediaPlayerListener);
        mPlayer.setOnCompletionListener(mIjkMediaPlayerListener);
        mPlayer.setOnInfoListener(mIjkMediaPlayerListener);
        mPlayer.setOnSeekCompleteListener(mIjkMediaPlayerListener);
        mPlayer.setOnTimedTextListener(mIjkMediaPlayerListener);
        mPlayer.setOnVideoSizeChangedListener(mIjkMediaPlayerListener);
        mPlayer.setScreenOnWhilePlaying(true);
    }

    private SurfaceHolder.Callback2 mSurfaceHolderCallback = new SurfaceHolder.Callback2() {
        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {
            KLog.w("holder = " + holder);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            KLog.w("holder = " + holder);
            mPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            KLog.w("holder = " + holder + ", format = " + format + ", width = " + width + ", height = " + height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.w("holder = " + holder);
            mPlayer.stop();
        }
    };

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException {
        //mPlayer.release();
        mPlayer.setDataSource(context, uri);
        mPlayer.prepareAsync();
    }


    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) throws IOException {
        //mPlayer.release();
        mPlayer.setDataSource(context, uri, headers);
        mPlayer.prepareAsync();
    }

    @Override
    public void setDataSource(FileDescriptor fd) throws IOException {
        //mPlayer.release();
        mPlayer.setDataSource(fd);
        mPlayer.prepareAsync();
    }

    @Override
    public void setDataSource(String path) throws IOException {
        //mPlayer.release();
        mPlayer.setDataSource(path);
        mPlayer.prepareAsync();
    }

    @Override
    public String getDataSource() {
        return mPlayer.getDataSource();
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    public void stop() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private IjkMediaPlayerComponentListener mIjkMediaPlayerListener = new IjkMediaPlayerComponentListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            KLog.d(TAG, "percent = " + percent);
        }

        @Override
        public void onCompletion(IMediaPlayer mp) {
            KLog.d(TAG, "");
        }

        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            KLog.d(TAG, "what = " + what + ", extra = " + extra);
            return false;
        }

        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            // KLog.d(TAG, "what = " + what + ", extra = " + extra);
            return false;
        }

        @Override
        public void onPrepared(IMediaPlayer mp) {
            KLog.d(TAG, "");
        }

        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            KLog.d(TAG, "");
        }

        @Override
        public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
            KLog.d(TAG, "text = " + text);
        }

        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height,
                                       int sar_num, int sar_den) {
            KLog.d(TAG, "width = " + width + ", height = " + height + ", sar_num = " + sar_num + ", sar_den = " + sar_den);
            mSurfaceView.setAspectRatio(width, height);
        }
    };
}
