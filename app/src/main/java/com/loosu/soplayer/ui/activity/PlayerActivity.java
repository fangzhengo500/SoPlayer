package com.loosu.soplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.loosu.soplayer.R;
import com.loosu.soplayer.ui.activity.base.BaseActivity;
import com.loosu.soplayer.widget.videoview.SoVideoView;

public class PlayerActivity extends BaseActivity {
    private static final String KEY_PATH = "key_path";

    public static Intent getStartIntent(Context context, String path) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(KEY_PATH, path);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        SoVideoView videoView = findViewById(R.id.video_view);
        videoView.setDataSource(getIntent().getStringExtra(KEY_PATH));
        //videoView.setController(new SimpleController(this));
    }
}
