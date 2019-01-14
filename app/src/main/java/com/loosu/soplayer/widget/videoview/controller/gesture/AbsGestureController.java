package com.loosu.soplayer.widget.videoview.controller.gesture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.TimeUtil;
import com.loosu.soplayer.widget.SoProgressBar;
import com.loosu.soplayer.widget.videoview.controller.Controller;
import com.loosu.soplayer.widget.videoview.interfaces.IController;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;

import java.util.Locale;

public abstract class AbsGestureController extends Controller {
    private static final String TAG = "AbsGestureController";

    // 手势 - 声量
    protected SoProgressBar mProgressVolume;
    // 手势 - 亮度
    protected SoProgressBar mProgressScreenBright;
    // 手势 - 进度
    protected View mLayoutSeek;
    protected TextView mTvSeekPosition;
    protected TextView mTvSeekDuration;
    protected ProgressBar mProgressBarSeek;
    // 开始/暂停 按钮
    protected ImageView mBtnPauseOrResume;
    // 顶部栏
    protected View mLayoutTop;
    protected TextView mTvTitle;

    // 底部栏
    protected View mLayoutBottom;
    protected TextView mTvBottomCurrentPosition;
    protected TextView mTvBottomDuration;
    protected SeekBar mBottomSeekBar;

    public AbsGestureController(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_gesture_controller, this, true);
        // 手势 - 声量
        mProgressVolume = findViewById(R.id.progress_volume);
        // 手势 - 亮度
        mProgressScreenBright = findViewById(R.id.progress_screen_brightness);
        // 手势 - 进度
        mLayoutSeek = findViewById(R.id.layout_seek);
        mTvSeekPosition = findViewById(R.id.tv_seek_position);
        mTvSeekDuration = findViewById(R.id.tv_seek_duration);
        mProgressBarSeek = findViewById(R.id.progress_bar_seek);

        // 开始 or 暂停
        mBtnPauseOrResume = findViewById(R.id.btn_pause_or_resume);

        // 顶部栏
        mLayoutTop = findViewById(R.id.layout_top);
        mTvTitle = findViewById(R.id.tv_title);
        // 底部栏
        mLayoutBottom = findViewById(R.id.layout_bottom);
        mTvBottomCurrentPosition = findViewById(R.id.tv_bottom_current_position);
        mTvBottomDuration = findViewById(R.id.tv_bottom_duration);
        mBottomSeekBar = findViewById(R.id.bottom_seekbar);


        mBtnPauseOrResume.setOnClickListener(mClickListener);
        mBottomSeekBar.setOnSeekBarChangeListener(mSeekListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mHideRunnable);
        removeCallbacks(mProgressUpdateRunnable);
    }

    @Override
    public void show(long duration) {
        super.show(duration);
        setTitle(mPlayer.getDataSource());
        showBtnPauseOrResume();
        updateProgress();
        showLayoutTop();
        showLayoutBottom();

        if (duration > 0) {
            // 自动隐藏
            removeCallbacks(mHideRunnable);
            postDelayed(mHideRunnable, IController.SHOW_AUTO_HIDE_DEFAULT);
        } else {
            removeCallbacks(mHideRunnable);
        }

        mProgressUpdateRunnable.run();
    }

    @Override
    public void hide() {
        super.hide();
        hideBtnPauseOrResume();
        hideLayoutTop();
        hideLayoutBottom();
        hideBrightChange();
        hideVolumeChange();
        hideSeekChange();
        removeCallbacks(mProgressUpdateRunnable);
    }

    protected void showLayoutTop() {
        mLayoutTop.setVisibility(VISIBLE);
    }

    protected void hideLayoutTop() {
        mLayoutTop.setVisibility(GONE);
    }

    protected void showBtnPauseOrResume() {
        mBtnPauseOrResume.setVisibility(VISIBLE);
    }

    protected void hideBtnPauseOrResume() {
        mBtnPauseOrResume.setVisibility(GONE);
    }

    protected void showLayoutBottom() {
        mLayoutBottom.setVisibility(VISIBLE);
    }

    protected void hideLayoutBottom() {
        mLayoutBottom.setVisibility(GONE);
    }

    public void showBrightChange(float present) {
        mProgressScreenBright.setVisibility(VISIBLE);
        mProgressScreenBright.setProgress((int) (mProgressScreenBright.getMax() * present));
        mProgressScreenBright.setText(String.format(Locale.US, "%.1f", present));
    }

    public void hideBrightChange() {
        mProgressScreenBright.setVisibility(GONE);
    }

    public void showVolumeChange(float present) {
        mProgressVolume.setVisibility(VISIBLE);
        mProgressVolume.setProgress((int) (mProgressVolume.getMax() * present));
        mProgressVolume.setText(String.format(Locale.US, "%.1f", present));
    }

    public void hideVolumeChange() {
        mProgressVolume.setVisibility(GONE);
    }

    public void showSeekChange(long position, long duration) {
        float present = position * 1f / duration;
        mTvSeekPosition.setText(TimeUtil.formatDuration(position));
        mTvSeekDuration.setText(TimeUtil.formatDuration(duration));
        mProgressBarSeek.setProgress((int) (present * mProgressBarSeek.getMax()));
        mLayoutSeek.setVisibility(VISIBLE);

        mBottomSeekBar.setProgress((int) (present * mProgressBarSeek.getMax()));
        removeCallbacks(mProgressUpdateRunnable);
    }

    public void hideSeekChange() {
        mLayoutSeek.setVisibility(GONE);
        postDelayed(mProgressUpdateRunnable, 1000);
    }

    public long getDuration() {
        return mPlayer == null ? 0 : mPlayer.getDuration();
    }

    public long getCurrentPosition() {
        return mPlayer == null ? 0 : mPlayer.getCurrentPosition();
    }

    private void onClickBtnPlay() {
        startOrPausePlayer();
        updateBtnPlay();
        show(IController.SHOW_AUTO_HIDE_DEFAULT);
    }

    private final OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            onClickBtnPlay();
        }
    };

    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            removeCallbacks(mHideRunnable);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            long duration = mPlayer.getDuration();
            float percent = mBottomSeekBar.getProgress() * 1f / mBottomSeekBar.getMax();
            long newPosition = (long) (duration * percent);
            seekTo((int) newPosition);
            if (mTvBottomCurrentPosition != null) {
                mTvBottomCurrentPosition.setText(TimeUtil.formatDuration(newPosition));
            }

            updateProgress();
            updateBtnPlay();
        }
    };

    public void updateBtnPlay() {
        if (mBtnPauseOrResume == null)
            return;
        if (mPlayer == null) {
            KLog.i(TAG, "player = null, 显示 ▲ 样式.");
            mBtnPauseOrResume.setImageResource(R.drawable.controller_btn_resume_drawable);

        } else if (mPlayer.getState() == IMediaController.State.STARTED || mPlayer.getState() == IMediaController.State.PREPARING || mPlayer.getState() == IMediaController.State.PREPARED) {
            KLog.i(TAG, "playing = " + mPlayer.isPlaying() + " state = " + mPlayer.getState() + " 显示 || 样式.");
            mBtnPauseOrResume.setImageResource(R.drawable.controller_btn_pause_drawable);

        } else {
            KLog.i(TAG, "playing = " + mPlayer.isPlaying() + " state = " + mPlayer.getState() + " 显示 ▲ 样式.");
            mBtnPauseOrResume.setImageResource(R.drawable.controller_btn_resume_drawable);
        }
    }

    protected void updateProgress() {
        final IMediaController player = mPlayer;
        if (player == null) {
            return;
        }

        final long currentPosition = player.getCurrentPosition();
        final long duration = player.getDuration();

        if (player.getState() == IMediaController.State.PLAYBACK_COMPLETED) {
            mBottomSeekBar.setProgress(mBottomSeekBar.getMax());
            mTvBottomCurrentPosition.setText(TimeUtil.formatDuration(duration));
            updateBtnPlay();
            return;
        }

        if (mBottomSeekBar != null) {
            if (duration > 0) {
                float percent = currentPosition * 1f / duration;
                mBottomSeekBar.setProgress((int) (mBottomSeekBar.getMax() * percent));
            }
            mBottomSeekBar.setSecondaryProgress((int) (mBottomSeekBar.getMax() * mPlayer.getBufferPercentage()));
        }

        if (mTvBottomCurrentPosition != null) {
            mTvBottomCurrentPosition.setText(TimeUtil.formatDuration(currentPosition));
        }

        if (mTvBottomDuration != null) {
            mTvBottomDuration.setText(TimeUtil.formatDuration(duration));

        }
    }

    public void setTitle(CharSequence text) {
        mTvTitle.setText(text);
    }

    public void seekTo(int newPosition) {
        if (mPlayer != null) {
            mPlayer.seeKTo(newPosition);
        }
    }

    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private Runnable mProgressUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
            removeCallbacks(mProgressUpdateRunnable);
            if (mPlayer != null && mPlayer.isPlaying()) {
                postDelayed(mProgressUpdateRunnable, 1000);
            }
        }
    };

    public abstract static class Detector {
        protected final AbsGestureController mController;

        public Detector(AbsGestureController controller) {
            mController = controller;
        }

        public abstract void onControllerSizeChanged(int w, int h, int oldw, int oldh);

        public abstract boolean onTouchEvent(MotionEvent event);

        public abstract boolean isHandling();
    }
}
