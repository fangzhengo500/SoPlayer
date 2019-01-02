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
import com.loosu.soplayer.widget.videoview.BaseSoVideoView;

import java.io.IOException;

public class VideoViewTestFragment extends Fragment {
    private static final String TAG = "VideoViewTestFragment";

    private static final String KEY_VIDEO = "VIDEO";

    private VideoEntry mVideo;

    private BaseSoVideoView mVideoView;

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
        mVideoView.setOnClickListener(mClickListener);
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
        //mVideoView.start();
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

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //mVideoView.setDataSource("http://ivytest.i-weiying.com/74ba/video/20181222/20181222cceea418d72ed7ba9af883349500cba91545449583751.mp4?auth_key=1546414425-0-0-1620ba10b74f9e1cbade437b10b6f426");
                if (getArguments() != null) {
                    Bundle bundle = getArguments();
                    mVideo = bundle.getParcelable(KEY_VIDEO);
                    mVideoView.setDataSource(mVideo.getData());
                    mVideoView.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
