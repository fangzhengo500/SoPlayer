package com.loosu.soplayer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.TimeUtil;

import java.util.List;

public class VideoCardAdapter extends DocumentVideoAdapter {
    public VideoCardAdapter(@Nullable List<VideoEntry> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_video_card;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<VideoEntry> datas) {
        Context context = holder.itemView.getContext();

        VideoEntry videoEntry = getItem(position);

        holder.setText(R.id.tv_title, videoEntry.getDisplayName());
        holder.setText(R.id.tv_duration, TimeUtil.formatDuration(videoEntry.getDuration()));
        holder.setText(R.id.tv_size, Formatter.formatShortFileSize(context, videoEntry.getSize()));
        holder.setText(R.id.tv_display_size, videoEntry.getWidth() + "X" + videoEntry.getHeight());

        Glide.with(holder.itemView)
                .load(videoEntry.getData())
                .into((ImageView) holder.getView(R.id.iv_cover));
    }
}