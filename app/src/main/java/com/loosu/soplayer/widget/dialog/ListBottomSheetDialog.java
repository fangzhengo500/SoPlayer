package com.loosu.soplayer.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.loosu.soplayer.R;

public class ListBottomSheetDialog extends BottomSheetDialog {

    private TextView mTvTitle;
    private RecyclerView mViewList;

    private RecyclerView.Adapter mAdapter;

    public ListBottomSheetDialog(@NonNull Context context) {
        super(context);
        initDialog(context, 0);
    }


    public ListBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        initDialog(context, theme);
    }

    private void initDialog(Context context, int theme) {
        setContentView(R.layout.dialog_list_bottom_sheet);
        findView(context);
        initView(context);
    }

    private void findView(Context context) {
        mTvTitle = findViewById(R.id.tv_title);
        mViewList = findViewById(R.id.view_list);
    }

    private void initView(Context context) {
        mViewList.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mViewList.setAdapter(adapter);
    }
}
