package com.loosu.soplayer.adapter;

import android.support.annotation.Nullable;

import com.loosu.soplayer.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.soplayer.domain.VideoEntry;

import java.util.List;

public abstract class DocumentVideoAdapter extends ARecyclerAdapter<VideoEntry> {
    public DocumentVideoAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    public VideoEntry getItem(int position) {
        return mDatas.get(position);
    }
}
