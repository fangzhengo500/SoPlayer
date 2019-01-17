package com.loosu.soplayer.adapter;

import android.support.annotation.Nullable;

import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.widget.videoview.AbsSoVideoView;
import java.util.List;

public class PreViewVideoAdapter extends DocumentVideoAdapter {
    public PreViewVideoAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_preview_video;
    }

//    @Override
//    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerHolder holder = super.onCreateViewHolder(parent, viewType);
//        SoVideoView videoView = holder.getView(R.id.video_view);
//        return holder;
//    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<VideoEntry> datas) {
        VideoEntry videoEntry = getItem(position);

        AbsSoVideoView videoView = holder.getView(R.id.video_view);
        videoView.setDataSource(videoEntry.getData());
    }


}
