package com.loosu.test.videoview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;

public class VideoViewTestActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewTestActivity";

    private static final String KEY_VIDEO = "VIDEO";

    private VideoEntry mVideo;

    public static Intent getStartIntent(Context context, VideoEntry videoEntry) {
        Intent intent = new Intent(context, VideoViewTestActivity.class);
        intent.putExtra(KEY_VIDEO, videoEntry);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_test);

        Intent intent = getIntent();
        mVideo = intent.getParcelableExtra(KEY_VIDEO);

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_VIDEO, mVideo);

        VideoViewTestFragment fragment = (VideoViewTestFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_view_test);
        fragment.setArguments(bundle);
    }

    @Override
    protected void onStart() {
        KLog.d(TAG, "");
        super.onStart();
    }

    @Override
    protected void onResume() {
        KLog.d(TAG, "");
        super.onResume();
    }

    @Override
    protected void onPause() {
        KLog.d(TAG, "");
        super.onPause();
    }

    @Override
    protected void onStop() {
        KLog.d(TAG, "");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        KLog.d(TAG, "");
        super.onDestroy();
    }
}
