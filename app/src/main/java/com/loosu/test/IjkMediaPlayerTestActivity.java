package com.loosu.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.AutoFixSurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class IjkMediaPlayerTestActivity extends AppCompatActivity implements
        IMediaPlayer.OnPreparedListener, IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnTimedTextListener, IMediaPlayer.OnVideoSizeChangedListener, View.OnClickListener {

    private static final String TAG = "IjkMediaPlayerTestActiv";
    private static final String KEY_VIDEO = "VIDEO";

    private IjkMediaPlayer mMediaPlayer;

    private AutoFixSurfaceView mSurfaceView;

    private VideoEntry mVideo;

    private View mBtnReset;
    private View mBtnSetData;
    private View mBtnPrepare;
    private View mBtnStart;
    private View mBtnStop;
    private View mBtnPause;

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
    }

    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(KEY_VIDEO);

        mMediaPlayer = new IjkMediaPlayer();
    }

    private void findView(Bundle savedInstanceState) {
        mSurfaceView = findViewById(R.id.surface_view);

        mBtnReset = findViewById(R.id.btn_reset);
        mBtnSetData = findViewById(R.id.btn_set_data);
        mBtnPrepare =findViewById(R.id.btn_prepare);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnPause = findViewById(R.id.btn_pause);
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

        mSurfaceView.getHolder().addCallback(mCallback2);

        mBtnReset.setOnClickListener(this);
        mBtnSetData.setOnClickListener(this);
        mBtnPrepare.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                mMediaPlayer.reset();
                break;
            case R.id.btn_set_data:
                try {
                    mMediaPlayer.setDataSource(mVideo.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_prepare:
                mMediaPlayer.setDisplay(mSurfaceView.getHolder());
                mMediaPlayer.prepareAsync();
                break;
            case R.id.btn_start:
                mMediaPlayer.start();
                break;
            case R.id.btn_stop:
                mMediaPlayer.stop();
                break;
            case R.id.btn_pause:
                mMediaPlayer.pause();
                break;
        }
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        KLog.w(TAG, "percent = " + percent);
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
        KLog.w(TAG, "");
    }

    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        KLog.w(TAG, "what = " + IjkMediaPlayerUtil.errorToString(what) + ", extra = " + extra);
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        KLog.w(TAG, "what = " + IjkMediaPlayerUtil.infoToString(what) + ", extra = " + extra);
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {
        KLog.w(TAG, "");
    }


    @Override
    public void onSeekComplete(IMediaPlayer mp) {
        KLog.d(TAG, "");
    }

    @Override
    public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
        KLog.w(TAG, "text = " + text);
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        KLog.w(TAG, "width = " + width + ", height = " + height + ", sar_num = " + sar_num + ", sar_den = " + sar_den);
        mSurfaceView.setAspectRatio(width, height);
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
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            KLog.d(TAG, "holder = " + holder + ", format = " + PixelFormatUtil.formatToString(format) + ", width = " + width + ", height = " + height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            KLog.d(TAG, "holder = " + holder);
        }
    };
}
