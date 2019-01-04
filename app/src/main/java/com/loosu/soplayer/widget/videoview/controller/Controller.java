package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.TimeUtil;
import com.loosu.soplayer.widget.videoview.interfaces.IController;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;


public class Controller extends FrameLayout implements IController {
    private static final String TAG = "AbsSoVideoView";

    private IMediaController mPlayer;

    private final ImageView mBtnPauseOrResume;

    private final TextView mTvCurrentPosition;
    private final TextView mTvDuration;
    private final SeekBar mProgress;

    public Controller(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_controller, this, true);
        mBtnPauseOrResume = findViewById(R.id.btn_pause_or_resume);

        mTvCurrentPosition = findViewById(R.id.tv_current_position);
        mTvDuration = findViewById(R.id.tv_duration);
        mProgress = findViewById(R.id.progress);

        mBtnPauseOrResume.setOnClickListener(mClickListener);
        mProgress.setOnSeekBarChangeListener(mSeekListener);

        updateBtnPauseOrResume();
    }

    public void setMediaPlayer(IMediaController player) {
        mPlayer = player;
        updateBtnPauseOrResume();
    }

    @Override
    public void show() {
        post(mShowProgress);
    }

    @Override
    public void hide() {
        removeCallbacks(mShowProgress);
    }

    private void setProgress() {
        IMediaController player = mPlayer;
        if (player == null) {
            return;
        }

        long currentPosition = player.getCurrentPosition();
        long duration = player.getDuration();

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

    public void updateBtnPauseOrResume() {
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

    private final View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mPlayer.getState() == IMediaController.State.STARTED) {
                KLog.d(TAG, "暂停 - pause");
                mPlayer.pause();

            } else if (mPlayer.getState() == IMediaController.State.PAUSED || mPlayer.getState() == IMediaController.State.PLAYBACK_COMPLETED) {
                KLog.d(TAG, "恢复 - resume");
                mPlayer.resume();

            } else {
                mPlayer.start();

            }
            updateBtnPauseOrResume();
        }
    };

    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            removeCallbacks(mShowProgress);
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
            updateBtnPauseOrResume();
            post(mShowProgress);
        }
    };

    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            setProgress();

            removeCallbacks(mShowProgress);
            postDelayed(mShowProgress, 1000);
        }
    };
}
