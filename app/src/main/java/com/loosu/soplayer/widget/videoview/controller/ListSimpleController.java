package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.TimeUtil;
import com.loosu.soplayer.widget.videoview.controller.gesture.AbsGestureController;
import com.loosu.soplayer.widget.videoview.detector.ClickDetector;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;

public class ListSimpleController extends AbsGestureController implements View.OnClickListener {
    private static final String TAG = "ListSimpleController";

    private ImageView mBtnPauseOrResume;
    private View mLayoutBottom;
    private TextView mTvBottomCurrentPosition;
    private TextView mTvBottomDuration;
    private SeekBar mBottomSeekBar;
    private View mBtnFullscreen;

    public ListSimpleController(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_list_simple, this, true);
        initController(context);
    }

    private void initController(Context context) {
        mBtnPauseOrResume = findViewById(R.id.btn_pause_or_resume);
        mLayoutBottom = findViewById(R.id.layout_bottom);
        mTvBottomCurrentPosition = findViewById(R.id.tv_bottom_current_position);
        mTvBottomDuration = findViewById(R.id.tv_bottom_duration);
        mBottomSeekBar = findViewById(R.id.bottom_seekbar);
        mBtnFullscreen = findViewById(R.id.btn_fullscreen);

        mBtnPauseOrResume.setOnClickListener(this);
        mBtnFullscreen.setOnClickListener(this);
        mBottomSeekBar.setOnSeekBarChangeListener(mSeekListener);

        addGestureDetector(new ClickDetector(context, this));
    }

    @Override
    public void startOrPausePlayer() {
        super.startOrPausePlayer();

    }

    private void onClickBtnPauseOrResume() {
        startOrPausePlayer();
        updateBtnPlay();
        show(Controller.SHOW_AND_NEVER_HIDE);
    }

    private void onClickFullscreen() {

    }

    @Override
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

    @Override
    public void show(long duration) {
        super.show(duration);
        showBtnPauseOrResume();
        showLayoutBottom();
    }


    @Override
    public void hide() {
        super.hide();
        hideBtnPauseOrResume();
        hideLayoutBottom();
    }

    private void showBtnPauseOrResume() {
        mBtnPauseOrResume.setVisibility(VISIBLE);
    }

    private void hideBtnPauseOrResume() {
        mBtnPauseOrResume.setVisibility(GONE);
    }

    private void showLayoutBottom() {
        mLayoutBottom.setVisibility(VISIBLE);
        mProgressUpdateRunnable.run();
    }

    private void hideLayoutBottom() {
        mLayoutBottom.setVisibility(GONE);
        removeCallbacks(mProgressUpdateRunnable);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pause_or_resume:
                onClickBtnPauseOrResume();
                break;

            case R.id.btn_fullscreen:
                onClickFullscreen();
                break;
        }
    }
    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
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
}
