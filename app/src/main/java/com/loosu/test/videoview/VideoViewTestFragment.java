package com.loosu.test.videoview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.soplayer.R;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.SoVideoView;

import java.util.ArrayList;

public class VideoViewTestFragment extends Fragment {
    private static final String TAG = "VideoViewTestFragment";

    private static final String KEY_VIDEOS = "key_videos";
    private static final String KEY_POSITION = "key_position";

    private ArrayList<VideoEntry> mVideos;
    private int mPosition;

    private SoVideoView mVideoView;
    private RecyclerView mViewList;

    @Override
    public void onAttach(Context context) {
        KLog.d(TAG, "context = " + context);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        KLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mVideos = (ArrayList<VideoEntry>) arguments.getSerializable(KEY_VIDEOS);
            mPosition = arguments.getInt(KEY_POSITION);
        }
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
        mViewList = view.findViewById(R.id.view_list);

        mViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewList.setAdapter(new VideoViewAdapter(mVideos));
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
