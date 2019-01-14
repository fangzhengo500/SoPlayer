package com.loosu.soplayer.widget.videoview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.controller.gesture.GestureController;
import com.loosu.soplayer.widget.videoview.interfaces.IController;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SoVideoView extends AbsSoVideoView implements View.OnClickListener {
    private static final String TAG = "BaseSoVideoView";

    private IjkMediaPlayer mPlayer = new IjkMediaPlayer();

    private float mBufferPercentage;

    private AutoFixSurfaceView mSurfaceView;
    private ImageView mIvCover;

    private int mVideoWidth = Integer.MIN_VALUE;
    private int mVideoHeight = Integer.MIN_VALUE;

    private IController mController;

    private float mDownX;
    private float mDownY;

    public SoVideoView(@NonNull Context context) {
        this(context, null);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        mSurfaceView = findViewById(R.id.surface_view);
        mIvCover = findViewById(R.id.iv_cover);

        GestureController controller = new GestureController(context);
        controller.setMediaPlayer(this);
        mController = controller;

        addView(controller);
        setOnClickListener(this);
    }

    protected int getLayoutId() {
        return R.layout.widget_base_so_player;
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        super.setDataSource(context, uri);

        mController.show(IController.SHOW_AND_NEVER_HIDE);
        showCover();
        Glide.with(this)
                .load(uri)
                .into(mIvCover);
    }

    @Override
    public void setDataSource(String path) {
        super.setDataSource(path);

        mController.show(IController.SHOW_AND_NEVER_HIDE);
        showCover();
        Glide.with(this)
                .load(path)
                .into(mIvCover);
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
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        showCover();
    }

    @Override
    protected void onPrepared(IMediaPlayer mp) {
        super.onPrepared(mp);
        if (mController != null) {
            mController.show(IController.SHOW_AUTO_HIDE_DEFAULT);
        }
    }

    @Override
    protected void onStarted(IMediaPlayer mp) {
        super.onStarted(mp);
        showVideo();
    }

    @Override
    protected void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        if (mVideoWidth != width || mVideoHeight != height) {
            mVideoWidth = width;
            mVideoHeight = height;
            mSurfaceView.setVisibility(VISIBLE);
            mSurfaceView.setAspectRatio(width, height);
        }
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
            mController.show(IController.SHOW_AUTO_HIDE_DEFAULT);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mDownX;
                float dy = ev.getY() - mDownY;
                if (Math.abs(dx) < Math.abs(dy) && Math.abs(dy) > 50) {
                    // 垂直滑动
                    getParent().requestDisallowInterceptTouchEvent(false);

                } else {
                    // 水平滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    private void showCover() {
        mIvCover.setVisibility(VISIBLE);
        mSurfaceView.setVisibility(GONE);
    }

    private void showVideo() {
        mSurfaceView.setVisibility(VISIBLE);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIvCover.setVisibility(GONE);
            }
        });
        animator.start();
    }
}
