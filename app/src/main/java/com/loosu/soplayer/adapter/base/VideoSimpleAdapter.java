package com.loosu.soplayer.adapter.base;

import android.support.annotation.Nullable;

import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.soplayer.domain.VideoEntry;

import java.util.List;

public class VideoSimpleAdapter extends ARecyclerAdapter<VideoEntry> {

    public VideoSimpleAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_video_simple;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<VideoEntry> datas) {

    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
