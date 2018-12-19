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
import com.loosu.soplayer.adapter.VideoSimpleAdapter;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.DataHelper;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.SystemUiUtil;
import com.loosu.soplayer.widget.SoToolbar;

import java.util.List;

public class DocumentsFragment extends Fragment {
    private static final String TAG = "DocumentsFragment";

    private SoToolbar mToolbar;
    private RecyclerView mViewList;
    private VideoSimpleAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

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
        initListener(view, savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        List<VideoEntry> videoEntries = DataHelper.getVideos(getContext());
        mAdapter = new VideoSimpleAdapter(videoEntries);
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

        // toolbar - title
        mToolbar.setTitle(R.string.documents_label);
        Toolbar toolbar;

        // toolbar - navigation
        mToolbar.setNavigationIcon(R.drawable.ic_action_menu_click_drawable);
        mToolbar.setNavigationBackgroundResource(R.drawable.toolbar_navigation_background);

        // toolbar - position
        mToolbar.setPositionIcon(R.drawable.ic_action_more_vert);

        // viewList
        mViewList.setLayoutManager(new LinearLayoutManager(context));
        mViewList.setAdapter(mAdapter);
    }

    private void initListener(View view, Bundle savedInstanceState) {
        mToolbar.setNavigationClickListener(mToolBarNavigationOnClickListener);
    }

    private void onClickToolBarNavigation(View v) {
        // TODO: 2018/12/19/019 展开侧边栏
        KLog.d(TAG, " v = "+v);
    }

    private View.OnClickListener mToolBarNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickToolBarNavigation(v);
        }
    };
}
