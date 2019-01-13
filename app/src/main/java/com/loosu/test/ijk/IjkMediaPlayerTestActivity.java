package com.loosu.test.ijk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.IjkMediaPlayerUtil;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.PixelFormatUtil;
import com.loosu.soplayer.widget.videoview.AutoFixSurfaceView;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class IjkMediaPlayerTestActivity extends AppCompatActivity implements
        IMediaPlayer.OnPreparedListener, IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnTimedTextListener, IMediaPlayer.OnVideoSizeChangedListener,
        IjkMediaPlayer.OnNativeInvokeListener,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "IjkMediaPlayerTestActiv";
    private static final String KEY_VIDEO = "VIDEO";

    private VideoEntry mVideo;
    private IMediaController.State mPlayerState = IMediaController.State.IDLE;

    private IjkMediaPlayer mMediaPlayer;

    private AutoFixSurfaceView mSurfaceView;

    private TextView mTvPlayerState;
    private TextView mTvDataSource;
    private TextView mTvVideoSize;
    private TextView mTvIsPlaying;
    private TextView mTvDuration;
    private TextView mTvMediaInfo;

    private Switch mToggleScreenOnWhilePlaying;
    private Switch mToggleLooping;

    private View mBtnReset;
    private View mBtnSetData;
    private View mBtnPrepare;
    private View mBtnStart;
    private View mBtnStop;
    private View mBtnPause;
    private SeekBar mSeekBarVideoSeek;
    private TextView mTvSeekValue;
    private View mBtnRelease;

    private SeekBar mSeekBarLeftVolume;
    private TextView mTvLeftVolumeValue;

    private SeekBar mSeekBarRightVolume;
    private TextView mTvRightVolumeValue;
    private TextView mTvPlayable;

    public static Intent getStartIntent(Context context, VideoEntry videoEntry) {
        Intent intent = new Intent(context, IjkMediaPlayerTestActivity.class);
        intent.putExtra(KEY_VIDEO, videoEntry);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_media_player_test);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);

        updateInfo(mMediaPlayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(KEY_VIDEO);
        mVideo.setData(mVideo.getData());
        mMediaPlayer = new IjkMediaPlayer();
    }

    private void findView(Bundle savedInstanceState) {
        mSurfaceView = findViewById(R.id.surface_view);

        mTvPlayable = findViewById(R.id.tv_playable);
        mTvPlayerState = findViewById(R.id.tv_player_state);
        mTvDataSource = findViewById(R.id.tv_data_source);
        mTvVideoSize = findViewById(R.id.tv_video_size);
        mTvIsPlaying = findViewById(R.id.tv_is_playing);
        mTvDuration = findViewById(R.id.tv_bottom_duration);
        mTvMediaInfo = findViewById(R.id.tv_media_info);

        mToggleScreenOnWhilePlaying = findViewById(R.id.toggle_screen_on_while_playing);
        mToggleLooping = findViewById(R.id.toggle_looping);

        mBtnReset = findViewById(R.id.btn_reset);
        mBtnSetData = findViewById(R.id.btn_set_data);
        mBtnPrepare = findViewById(R.id.btn_prepare);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnPause = findViewById(R.id.btn_pause);
        mBtnRelease = findViewById(R.id.btn_release);

        mSeekBarVideoSeek = findViewById(R.id.seek_bar_video_seek);
        mTvSeekValue = findViewById(R.id.tv_seek_value);

        mSeekBarLeftVolume = findViewById(R.id.seek_bar_left_volume);
        mTvLeftVolumeValue = findViewById(R.id.tv_left_volume_value);

        mSeekBarRightVolume = findViewById(R.id.seek_bar_right_volume);
        mTvRightVolumeValue = findViewById(R.id.tv_right_volume_value);
    }

    private void initView(Bundle savedInstanceState) {
    }

    private void initListener(Bundle savedInstanceState) {
        mMediaPlayer.setOnPreparedListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnErrorListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnBufferingUpdateListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnCompletionListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnInfoListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnSeekCompleteListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnTimedTextListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnVideoSizeChangedListener(IjkMediaPlayerTestActivity.this);
        mMediaPlayer.setOnNativeInvokeListener(this);   //断网重连必须实现
        mSurfaceView.getHolder().addCallback(mCallback2);

        mToggleScreenOnWhilePlaying.setOnCheckedChangeListener(this);
        mToggleLooping.setOnCheckedChangeListener(this);

        mBtnReset.setOnClickListener(this);
        mBtnSetData.setOnClickListener(this);
        mBtnPrepare.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnRelease.setOnClickListener(this);

        mSeekBarVideoSeek.setOnSeekBarChangeListener(this);
        mSeekBarLeftVolume.setOnSeekBarChangeListener(this);
        mSeekBarRightVolume.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                mMediaPlayer.reset();
                mPlayerState = IMediaController.State.IDLE;
                break;
            case R.id.btn_set_data:
                try {
                    mMediaPlayer.setDataSource(mVideo.getData());
                    mPlayerState = IMediaController.State.INITIALIZED;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_prepare:
                mMediaPlayer.setDisplay(mSurfaceView.getHolder());
                mMediaPlayer.prepareAsync();
                mPlayerState = IMediaController.State.PREPARING;
                break;
            case R.id.btn_start:
                mMediaPlayer.start();
                boolean playing = mMediaPlayer.isPlaying();
                KLog.d(TAG, "playing = " + playing);
                if (mPlayerState == IMediaController.State.PAUSED) {
                    mPlayerState = IMediaController.State.STARTED;
                }
                break;
            case R.id.btn_stop:
                mMediaPlayer.stop();
                mPlayerState = IMediaController.State.STOPPED;
                break;
            case R.id.btn_pause:
                mMediaPlayer.pause();
                mPlayerState = IMediaController.State.PAUSED;
                break;
            case R.id.btn_release:
                mMediaPlayer.release();
                mPlayerState = IMediaController.State.END;
                break;
        }
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggle_screen_on_while_playing:
                mMediaPlayer.setScreenOnWhilePlaying(isChecked);
                break;
            case R.id.toggle_looping:
                mMediaPlayer.setLooping(isChecked);
                break;
        }
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_bar_video_seek:
                mMediaPlayer.seekTo(seekBar.getProgress());
                long currentPosition = mMediaPlayer.getCurrentPosition();
                long duration = mMediaPlayer.getDuration();
                mTvSeekValue.setText(String.valueOf(currentPosition + "/" + duration));
                break;
            case R.id.seek_bar_left_volume:
            case R.id.seek_bar_right_volume:
                float leftVolume = mSeekBarLeftVolume.getProgress() * 1f / mSeekBarLeftVolume.getMax();
                float rightVolume = mSeekBarRightVolume.getProgress() * 1f / mSeekBarRightVolume.getMax();
                mMediaPlayer.setVolume(leftVolume, rightVolume);
                mTvLeftVolumeValue.setText(String.format("%.2f", leftVolume));
                mTvRightVolumeValue.setText(String.format("%.2f", rightVolume));
                break;
        }
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        KLog.w(TAG, "percent = " + percent);
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
        KLog.w(TAG, "");
        mPlayerState = IMediaController.State.PLAYBACK_COMPLETED;
        updateInfo(mMediaPlayer);
    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        KLog.w(TAG, "what = " + IjkMediaPlayerUtil.errorToString(getApplicationContext(), what) + ", extra = " + extra);
        mPlayerState = IMediaController.State.ERROR;
        updateInfo(mMediaPlayer);
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        KLog.w(TAG, "what = " + IjkMediaPlayerUtil.infoToString(getApplicationContext(), what) + ", extra = " + extra);

        if (mp.isPlaying() && (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START || what == IMediaPlayer.MEDIA_INFO_VIDEO_SEEK_RENDERING_START)) {
            mPlayerState = IMediaController.State.STARTED;
        }
        updateInfo(mMediaPlayer);
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        KLog.w(TAG, "");
        mPlayerState = IMediaController.State.PREPARED;
        long currentPosition = mp.getCurrentPosition();
        long duration = mp.getDuration();
        mTvSeekValue.setText(String.valueOf(currentPosition + "/" + duration));
        mSeekBarVideoSeek.setProgress((int) currentPosition);
        mSeekBarVideoSeek.setMax((int) duration);
        updateInfo(mMediaPlayer);
    }


    @Override
    public void onSeekComplete(IMediaPlayer mp) {
        KLog.d(TAG, "");
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
        KLog.w(TAG, "text = " + text);
        updateInfo(mMediaPlayer);
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        KLog.w(TAG, "width = " + width + ", height = " + height + ", sar_num = " + sar_num + ", sar_den = " + sar_den);
        mSurfaceView.setAspectRatio(width, height);
        updateInfo(mMediaPlayer);
    }

    @Override
    public boolean onNativeInvoke(int what, Bundle args) {
        KLog.w(TAG, "what = " + what + ", args = " + args);
        return false;
    }

    private void updateInfo(IMediaPlayer mp) {
        try {
            mTvPlayable.setText("playable : " + mp.isPlaying());
        } catch (Exception e) {
            e.printStackTrace();
            mTvPlayable.setText("playable : can not get info");
        }
        try {
            mTvPlayerState.setText("playable : " + mPlayerState);
        } catch (Exception e) {
            e.printStackTrace();
            mTvPlayerState.setText("playable : IDLE");
        }
        try {
            mTvDataSource.setText("data source: " + mp.getDataSource());
        } catch (Exception e) {
            e.printStackTrace();
            mTvDataSource.setText("data source: null");
        }

        try {
            mTvVideoSize.setText("video size: " + mp.getVideoWidth() + "X" + mp.getVideoHeight());
        } catch (Exception e) {
            e.printStackTrace();
            mTvVideoSize.setText("video size: 0X0");
        }

        try {
            mTvIsPlaying.setText("is playing: " + mp.isPlaying());
        } catch (Exception e) {
            e.printStackTrace();
            mTvIsPlaying.setText("is playing: false");
        }

        try {
            mTvDuration.setText("video duration: " + mp.getCurrentPosition() + "/" + mp.getDuration());
        } catch (Exception e) {
            e.printStackTrace();
            mTvDuration.setText("video duration: -1/-1");
        }

        try {
            mTvMediaInfo.setText("media info: " + IjkMediaPlayerUtil.mediaInfoToString(mp.getMediaInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            mTvMediaInfo.setText("media info: null");
        }
    }


    private SurfaceHolder.Callback2 mCallback2 = new SurfaceHolder.Callback2() {
        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {
            KLog.d(TAG, "holder = " + holder);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            KLog.d(TAG, "holder = " + holder);
            mMediaPlayer.setDisplay(holder);
            updateInfo(mMediaPlayer);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            KLog.d(TAG, "holder = " + holder + ", format = " + PixelFormatUtil.formatToString(format) + ", width = " + width + ", height = " + height);
            updateInfo(mMediaPlayer);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.d(TAG, "holder = " + holder);
            mMediaPlayer.stop();
            updateInfo(mMediaPlayer);
        }
    };
}
