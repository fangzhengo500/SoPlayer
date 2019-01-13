package com.loosu.soplayer.widget.videoview.controller;

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
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;

import java.util.Locale;

public abstract class AbsGestureController extends Controller {
    private static final String TAG = "GestureController";

    protected SoProgressBar mProgressVolume;          // 手势 - 声量
    protected SoProgressBar mProgressScreenBright;    // 手势 - 亮度
    protected View mLayoutSeek;                       // 手势 - 进度
    protected TextView mTvSeekPosition;
    protected TextView mTvSeekDuration;
    protected ProgressBar mProgressBarSeek;

    protected ImageView mBtnPauseOrResume;

    protected View mLayoutBottom;
    protected TextView mTvCurrentPosition;
    protected TextView mTvDuration;
    protected SeekBar mProgress;

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

        // 底部进度栏
        mLayoutBottom = findViewById(R.id.layout_bottom);
        mTvCurrentPosition = findViewById(R.id.tv_current_position);
        mTvDuration = findViewById(R.id.tv_duration);
        mProgress = findViewById(R.id.progress);


        mBtnPauseOrResume.setOnClickListener(mClickListener);
        mProgress.setOnSeekBarChangeListener(mSeekListener);
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }

        super.show();
        mBtnPauseOrResume.setVisibility(VISIBLE);
        mLayoutBottom.setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        if (!isShowing()) {
            return;
        }

        super.hide();
        mBtnPauseOrResume.setVisibility(GONE);
        mLayoutBottom.setVisibility(GONE);
        hideBrightChange();
        hideVolumeChange();
        hideSeekChange();
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
    }

    public void hideSeekChange() {
        mLayoutSeek.setVisibility(GONE);
    }

    public long getDuration() {
        return mPlayer == null ? 0 : mPlayer.getDuration();
    }

    public long getCurrentPosition() {
        return mPlayer == null ? 0 : mPlayer.getCurrentPosition();
    }

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

    private void onClickBtnPlay() {
        startOrPausePlayer();
        updateBtnPlay();
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

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            long duration = mPlayer.getDuration();
            float percent = mProgress.getProgress() * 1f / mProgress.getMax();
            long newPosition = (long) (duration * percent);
            mPlayer.seeKTo((int) newPosition);
            if (mTvCurrentPosition != null) {
                mTvCurrentPosition.setText(TimeUtil.formatDuration(newPosition));
            }

            setProgress();
            updateBtnPlay();
        }
    };

    protected void setProgress() {
        IMediaController player = mPlayer;
        if (player == null) {
            return;
        }

        long currentPosition = player.getCurrentPosition();
        long duration = player.getDuration();

        if (player.getState() == IMediaController.State.PLAYBACK_COMPLETED) {
            mProgress.setProgress(mProgress.getMax());
            mTvCurrentPosition.setText(TimeUtil.formatDuration(duration));
            return;
        }

        if (mProgress != null) {
            if (duration > 0) {
                float percent = currentPosition * 1f / duration;
                mProgress.setProgress((int) (mProgress.getMax() * percent));
            }
            mProgress.setSecondaryProgress((int) (mProgress.getMax() * mPlayer.getBufferPercentage()));
        }

        if (mTvCurrentPosition != null) {
            mTvCurrentPosition.setText(TimeUtil.formatDuration(currentPosition));
        }

        if (mTvDuration != null) {
            mTvDuration.setText(TimeUtil.formatDuration(duration));

        }
    }


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
