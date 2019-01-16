package com.loosu.soplayer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.base.recyclerview.ARecyclerAdapter;
import com.loosu.soplayer.adapter.base.recyclerview.RecyclerHolder;

import java.util.List;

public class WebVideoAdapter extends ARecyclerAdapter<String> {
    public WebVideoAdapter(@Nullable List<String> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_web_video;
    }

    @Override
    protected void onBindViewData(RecyclerHolder holder, int position, List<String> datas) {
        String url = getItem(position);
        holder.setText(R.id.tv_url, url);

        Glide.with(holder.itemView)
                .load(url)
                .into((ImageView) holder.getView(R.id.iv_cover));
    }

    @Override
    public String getItem(int position) {
        return mDatas.get(position);
    }
}
