package com.loosu.soplayer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.SystemUiUtil;

public class VideoPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
    }

    public void onClick(View view) {
        SystemUiUtil.toggleHideyBar(this);
    }
}
