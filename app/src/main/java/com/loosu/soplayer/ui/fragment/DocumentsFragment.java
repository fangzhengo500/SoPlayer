package com.loosu.soplayer.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.SystemUiUtil;
import com.loosu.soplayer.widget.SoToolbar;

public class DocumentsFragment extends Fragment {

    private SoToolbar mToolbar;
    private RecyclerView mViewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_documents, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    private void findView(View view, Bundle savedInstanceState) {
        mToolbar = view.findViewById(R.id.toolbar);
        mViewList = view.findViewById(R.id.view_list);
    }

    private void initView(View view, Bundle savedInstanceState) {
        final Context context = getContext();
        final Resources resources = getResources();

        // toolbar
        mToolbar.setPadding(mToolbar.getPaddingLeft(),
                mToolbar.getPaddingTop() + SystemUiUtil.getStatusBarHeight(context),
                mToolbar.getPaddingRight(),
                mToolbar.getPaddingBottom());

        // toolbar - navigation
        mToolbar.setNavigationIcon(R.drawable.ic_action_menu);
        mToolbar.setNavigationBackgroundColor(resources.getColor(R.color.rusty_red));

        // toolbar - position
        mToolbar.setPositionIcon(R.drawable.ic_action_more_vert);

        // viewList
        mViewList.setLayoutManager(new LinearLayoutManager(context));
    }
}
