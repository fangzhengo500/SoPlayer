package com.loosu.test.videoview;

import android.support.annotation.Nullable;

import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.SoVideoView;

import java.util.List;


public class VideoViewAdapter extends ARecyclerAdapter<VideoEntry> {
    private static final String TAG = "VideoViewAdapter";

    public VideoViewAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_video_view;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<VideoEntry> datas) {
        VideoEntry videoEntry = getItem(position);
        KLog.w(TAG, videoEntry.getData());

        SoVideoView videoView = holder.getView(R.id.video_view);
        videoView.setDataSource(videoEntry.getData());
    }

    @Override
    public VideoEntry getItem(int position) {
        return mDatas.get(position);
    }
}
