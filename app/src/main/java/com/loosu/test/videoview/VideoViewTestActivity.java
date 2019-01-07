package com.loosu.test.videoview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;

import java.util.ArrayList;
import java.util.List;

public class VideoViewTestActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewTestActivity";

    private static final String KEY_EXTRA = "key_extra";
    private static final String KEY_VIDEOS = "key_videos";
    private static final String KEY_POSITION = "key_position";

    private VideoEntry mVideo;

    public static Intent getStartIntent(Context context, List<VideoEntry> videos, int position) {
        ArrayList<VideoEntry> datas = new ArrayList<>();
        datas.addAll(videos);

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_VIDEOS, datas);
        bundle.putInt(KEY_POSITION, position);

        Intent intent = new Intent(context, VideoViewTestActivity.class);
        intent.putExtra(KEY_EXTRA, bundle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_test);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_EXTRA);

        //VideoViewTestFragment fragment = (VideoViewTestFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_view_test);
        KLog.d("set bundle");
        VideoViewTestFragment fragment = new VideoViewTestFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment_container, fragment)
                .commit();
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
