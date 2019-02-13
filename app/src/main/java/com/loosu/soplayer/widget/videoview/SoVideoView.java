package com.loosu.soplayer.widget.videoview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.IjkMediaPlayerUtil;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.controller.Controller;
import com.loosu.soplayer.widget.videoview.controller.gesture.AnimationGestureController;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SoVideoView extends AbsSoVideoView implements View.OnClickListener {
    private static final String TAG = "BaseSoVideoView";

    private IjkMediaPlayer mPlayer = new IjkMediaPlayer();

    private float mBufferPercentage;

    private AutoFixSurfaceView mSurfaceView;
    private ImageView mIvCover;
    private View mLoadingView;
    private TextView mTvTcp;

    private int mVideoWidth = Integer.MIN_VALUE;
    private int mVideoHeight = Integer.MIN_VALUE;

    private Controller mController;

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
        mLoadingView = findViewById(R.id.loading_view);
        mTvTcp = findViewById(R.id.tv_tcp);

        AnimationGestureController controller = new AnimationGestureController(context);
        controller.attachMediaPlayer(this);
        mController = controller;

        addView(controller);
        setOnClickListener(this);

        mPlayer.setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() {
            @Override
            public boolean onNativeInvoke(int i, Bundle bundle) {
                KLog.e(TAG, "i = " + IjkMediaPlayerUtil.errorToString(getContext(), i));
                return true;
            }
        });
    }

    protected int getLayoutId() {
        return R.layout.widget_base_so_player;
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        super.setDataSource(context, uri);

        mController.show(Controller.SHOW_AND_NEVER_HIDE);
        showCover();
        Glide.with(this)
                .load(uri)
                .into(mIvCover);
    }

    @Override
    public void setDataSource(String path) {
        super.setDataSource(path);

        mController.show(Controller.SHOW_AND_NEVER_HIDE);
        showCover();
        Glide.with(this)
                .load(path)
                .into(mIvCover);
    }

    public void setController(Controller controller) {
        KLog.w(TAG, "luwei  controller = " + controller);
        if (mController != null) {
            mController.hide();
            removeView(mController);
        }

        mController = controller;
        mController.attachMediaPlayer(this);
        addView(controller);
    }

    @Override
    public IjkMediaPlayer getMediaPlayer() {
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
            mController.show(Controller.SHOW_AUTO_HIDE_DEFAULT);
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
        mBufferPercentage = percent * 1f / 100;
    }

    @Override
    protected void onInfo(IMediaPlayer mp, int what) {
        switch (what) {
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                KLog.w(TAG, "11111 loading show");
                mLoadingView.setVisibility(VISIBLE);
                post(mUpdateTcpRunnable);
                break;

            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                KLog.w(TAG, "22222 loading hide");
                mLoadingView.setVisibility(GONE);
                removeCallbacks(mUpdateTcpRunnable);
                break;
        }
    }

    @Override
    public long getDuration() {
        return getMediaPlayer().getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return getMediaPlayer().getCurrentPosition();
    }

    @Override
    public float getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public void seeKTo(int position) {
        getMediaPlayer().seekTo(position);
    }

    @Override
    public boolean isPlaying() {
        return getMediaPlayer().isPlaying();
    }

    @Override
    public void onClick(View v) {
        if (mController.isShowing()) {
            mController.hide();
        } else {
            mController.show(Controller.SHOW_AUTO_HIDE_DEFAULT);
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


    protected void showCover() {
        mIvCover.setVisibility(VISIBLE);
        mSurfaceView.setVisibility(GONE);
    }

    protected void showVideo() {
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


    private Runnable mUpdateTcpRunnable = new Runnable() {
        @Override
        public void run() {
            mTvTcp.setText(Formatter.formatFileSize(getContext(), getMediaPlayer().getTcpSpeed()) + "/s");

            removeCallbacks(mUpdateTcpRunnable);
            postDelayed(mUpdateTcpRunnable, 500);
        }
    };
}
