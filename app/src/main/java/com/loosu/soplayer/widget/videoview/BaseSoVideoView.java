package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class BaseSoVideoView extends AbsSoVideoView {
    private static final String TAG = "BaseSoVideoView";

    private IMediaPlayer mPlayer = new IjkMediaPlayer();

    private SurfaceView mSurfaceView;

    public BaseSoVideoView(@NonNull Context context) {
        this(context, null);
    }

    public BaseSoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        KLog.w(TAG, "");
        LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        mSurfaceView = findViewById(R.id.surface_view);
    }

    protected int getLayoutId() {
        return R.layout.widget_base_so_player;
    }

    @Override
    protected IMediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    @Override
    protected SurfaceHolder getSurfaceHolder() {
        KLog.w(TAG, "");
        return mSurfaceView == null ? null : mSurfaceView.getHolder();
    }
}
