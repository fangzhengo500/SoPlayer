package com.loosu.soplayer.adapter.base.listview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loosu.soplayer.adapter.base.IAViewHolder;


/**
 * Created by LuWei on 2018/7/30/030.
 */

/**
 * 主要处理{@link BaseAdapter#getView(int, View, ViewGroup)} 减少findView的次数, 封装模板代码。
 * <p>
 * 模仿 {@link android.support.v7.widget.RecyclerView.Adapter} 增加
 * {@link AbsListAdapter#createViewHolder(int, View)}、
 * {@link AbsListAdapter#onBindViewData(ViewHolder, int)} 方法。
 *
 * @param <VH> ViewHolder
 */
public abstract class AbsListAdapter<VH extends AbsListAdapter.ViewHolder> extends BaseAdapter {

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(getItemViewLayoutId(position), parent, false);

            holder = createViewHolder(position, convertView);

            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();

        }

        onBindViewData(holder, position);
        return convertView;
    }

    /**
     * 根据position获取Itemt的layoutId
     *
     * @param position position
     * @return layoutId
     */
    protected abstract int getItemViewLayoutId(int position);

    /**
     * 创建ViewHolder
     *
     * @param position    position
     * @param convertView itemView
     * @return holder
     */
    protected abstract VH createViewHolder(int position, @NonNull View convertView);

    /**
     * 视图和数据绑定，相当于{@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param holder   holder
     * @param position position
     */
    protected abstract void onBindViewData(VH holder, int position);


    public static abstract class ViewHolder implements IAViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        @Override
        public View getItemView() {
            return itemView;
        }
    }
}
