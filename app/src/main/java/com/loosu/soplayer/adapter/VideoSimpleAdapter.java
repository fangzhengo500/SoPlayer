package com.loosu.soplayer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.webkit.MimeTypeMap;

import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.TimeUtil;

import java.util.List;

public class VideoSimpleAdapter extends DocumentVideoAdapter {

    public VideoSimpleAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_video_simple;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<VideoEntry> datas) {
        Context context = holder.itemView.getContext();

        VideoEntry videoEntry = getItem(position);

        holder.setText(R.id.tv_title, videoEntry.getDisplayName());
        holder.setText(R.id.tv_duration, TimeUtil.formatDuration(videoEntry.getDuration()));
        holder.setText(R.id.tv_size, Formatter.formatShortFileSize(context, videoEntry.getSize()));
        holder.setText(R.id.tv_display_size, videoEntry.getWidth() + "X" + videoEntry.getHeight());

        holder.setText(R.id.tv_mine_type, MimeTypeMap.getSingleton().getExtensionFromMimeType(videoEntry.getMimeType()));
    }
}
