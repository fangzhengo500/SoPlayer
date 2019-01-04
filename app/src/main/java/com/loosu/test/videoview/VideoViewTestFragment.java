package com.loosu.test.videoview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.SoVideoView;

public class VideoViewTestFragment extends Fragment {
    private static final String TAG = "VideoViewTestFragment";

    private static final String KEY_VIDEO = "VIDEO";

    private VideoEntry mVideo;

    private SoVideoView mVideoView;

    @Override
    public void onAttach(Context context) {
        KLog.d(TAG, "context = " + context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "container = " + container + ", savedInstanceState = " + savedInstanceState);
        return inflater.inflate(R.layout.fragment_video_view_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "view = " + view + ", savedInstanceState = " + savedInstanceState);
        mVideoView = view.findViewById(R.id.video_view);
        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                if (getArguments() != null) {
                    Bundle bundle = getArguments();
                    mVideo = bundle.getParcelable(KEY_VIDEO);
                    mVideoView.setDataSource(mVideo.getData());
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        KLog.d(TAG, "");
        super.onStart();
    }

    @Override
    public void onResume() {
        KLog.d(TAG, "");
        super.onResume();
    }

    @Override
    public void onPause() {
        KLog.d(TAG, "");
        super.onPause();
    }

    @Override
    public void onStop() {
        KLog.d(TAG, "");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        KLog.d(TAG, "");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        KLog.d(TAG, "");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        KLog.d(TAG, "");
        super.onDetach();
    }
}
