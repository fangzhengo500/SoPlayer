package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.controller.Controller;
import com.loosu.soplayer.widget.videoview.interfaces.IController;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SoVideoView extends AbsSoVideoView implements View.OnClickListener {
    private static final String TAG = "BaseSoVideoView";

    private IMediaPlayer mPlayer = new IjkMediaPlayer();

    private float mBufferPercentage;

    private AutoFixSurfaceView mSurfaceView;

    private IController mController;

    public SoVideoView(@NonNull Context context) {
        this(context, null);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        KLog.w(TAG, "");
        init(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        mSurfaceView = findViewById(R.id.surface_view);

        Controller controller = new Controller(context);
        controller.setMediaPlayer(this);
        mController = controller;

        addView(controller);
        setOnClickListener(this);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onPrepared(IMediaPlayer mp) {
        super.onPrepared(mp);
        if (mController != null) {
            mController.show();
        }
    }

    @Override
    protected void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        mSurfaceView.setAspectRatio(width, height);
    }

    @Override
    protected void onBufferingUpdate(IMediaPlayer mp, int percent) {
        mBufferPercentage = percent;
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public float getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public void seeKTo(int position) {
        mPlayer.seekTo(position);
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void onClick(View v) {
        if (mController.isShowing()) {
            mController.hide();
        } else {
            mController.show();
        }
    }
}
