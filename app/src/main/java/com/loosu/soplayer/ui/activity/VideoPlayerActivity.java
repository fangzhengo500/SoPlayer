package com.loosu.soplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.SystemUiUtil;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;

public class VideoPlayerActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayerActivity";

    private static final String KEY_VIDEO = "VIDEO";

    private VideoEntry mVideo;

    private TextureView mVideoView;
    private IjkExoMediaPlayer mPlayer;

    public static Intent getStartIntent(Context context, VideoEntry videoEntry) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(KEY_VIDEO, videoEntry);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    private void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(KEY_VIDEO);
    }

    private void findView(Bundle savedInstanceState) {
        mVideoView = findViewById(R.id.video_view);
    }

    private void initView(Bundle savedInstanceState) {
    }

    private void initListener(Bundle savedInstanceState) {

    }

    public void onClick(View view) {
        SystemUiUtil.toggleHideyBar(this);
    }
}
