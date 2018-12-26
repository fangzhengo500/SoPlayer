package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SoVideoView extends FrameLayout implements IVideoView {
    private static final String TAG = "SoVideoView";

    private IjkMediaPlayer mPlayer;
    private SurfaceView mSurfaceView;

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
    }

    private SurfaceHolder.Callback2 mSurfaceHolderCallback = new SurfaceHolder.Callback2() {
        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {
            KLog.d("holder = " + holder);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            KLog.d("holder = " + holder);
            mPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            KLog.i("holder = " + holder + ", format = " + format + ", width = " + width + ", height = " + height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.w("holder = " + holder);
        }
    };

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException {
        mPlayer.setDataSource(context, uri);
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) throws IOException {
        mPlayer.setDataSource(context, uri, headers);
    }

    @Override
    public void setDataSource(FileDescriptor fd) throws IOException {
        mPlayer.setDataSource(fd);
    }

    @Override
    public void setDataSource(String path) throws IOException {
        mPlayer.setDataSource(path);
    }

    @Override
    public String getDataSource() {
        return mPlayer.getDataSource();
    }

    @Override
    public void prepareAsync() {
        mPlayer.prepareAsync();
    }

    @Override
    public void start() {
        mPlayer.start();
        mPlayer.setLooping(true);
        //mPlayer.setDisplay(mSurfaceView.getHolder());
    }
}
