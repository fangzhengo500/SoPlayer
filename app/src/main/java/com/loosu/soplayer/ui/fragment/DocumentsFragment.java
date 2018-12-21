package com.loosu.soplayer.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.soplayer.R;
import com.loosu.soplayer.adapter.DocumentVideoAdapter;
import com.loosu.soplayer.adapter.VideoCardAdapter;
import com.loosu.soplayer.adapter.VideoSimpleAdapter;
import com.loosu.soplayer.business.comparator.VideoDurationComparator;
import com.loosu.soplayer.business.comparator.VideoNameComparator;
import com.loosu.soplayer.business.comparator.VideoSizeComparator;
import com.loosu.soplayer.business.comparator.VideoTypeComparator;
import com.loosu.soplayer.domain.VideoEntry;
import com.loosu.soplayer.utils.DataHelper;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.PopupMenuUtil;
import com.loosu.soplayer.utils.SystemUiUtil;
import com.loosu.soplayer.widget.SoToolbar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DocumentsFragment extends Fragment {
    private static final String TAG = "DocumentsFragment";

    private static final int VIEW_MODULE_DEFAULT = 0;
    private static final int VIEW_MODULE_CARD = 1;

    private SoToolbar mToolbar;
    private RecyclerView mViewList;

    private List<VideoEntry> mVideoEntries;

    private DocumentVideoAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private int mViewModule = VIEW_MODULE_DEFAULT;

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
        final Context context = getContext();

        mVideoEntries = DataHelper.getVideos(context);
        mAdapter = new VideoSimpleAdapter(mVideoEntries);

        mLayoutManager = new GridLayoutManager(context, 1);
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
        mToolbar.setPositionBackgroundResource(R.drawable.toolbar_position_background);
        // viewList
        mViewList.setLayoutManager(mLayoutManager);
        mViewList.setAdapter(mAdapter);
    }

    private void initListener(View view, Bundle savedInstanceState) {
        mToolbar.setNavigationClickListener(mToolBarNavigationOnClickListener);
        mToolbar.setPositionClickListener(mToolBarPositionOnClickListener);
    }

    private void onClickToolBarNavigation(View v) {
        // TODO: 2018/12/19/019 展开侧边栏
        KLog.d(TAG, " v = " + v);
    }

    private void onClickToolBarPosition(View v) {
        KLog.d(TAG, " v = " + v);
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.document_toolbar, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(mMenuItemClickListener);
    }

    private void onMenuItemSelectedViewModule(MenuItem menuItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), mToolbar, Gravity.RIGHT);
        popupMenu.getMenuInflater().inflate(R.menu.view_module, popupMenu.getMenu());
        popupMenu.getMenu().findItem(mViewModule == VIEW_MODULE_CARD ? R.id.menu_view_module_card : R.id.menu_view_module_list).setChecked(true);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(mMenuItemClickListener);
        PopupMenuUtil.forceShowIcon(popupMenu, true);
    }


    private void onMenuItemSelectedViewModuleList(MenuItem menuItem) {
        menuItem.setChecked(true);
        setViewModule(VIEW_MODULE_DEFAULT);
    }

    private void onMenuItemSelectedViewModuleCard(MenuItem menuItem) {
        menuItem.setChecked(true);
        setViewModule(VIEW_MODULE_CARD);
    }

    private void onMenuItemSelectedOrderModule(MenuItem menuItem) {
        PopupMenu popupMenu = new PopupMenu(getContext(), mToolbar, Gravity.RIGHT);
        popupMenu.getMenuInflater().inflate(R.menu.video_order_module, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(mMenuItemClickListener);
        PopupMenuUtil.forceShowIcon(popupMenu, true);
    }

    private void onMenuItemSelectedOrderModuleByName(MenuItem menuItem) {
        setVideoOderModule(new VideoNameComparator());
    }

    private void onMenuItemSelectedOrderModuleByDuration(MenuItem menuItem) {
        setVideoOderModule(new VideoDurationComparator());
    }

    private void onMenuItemSelectedOrderModuleBySize(MenuItem menuItem) {
        setVideoOderModule(new VideoSizeComparator());
    }

    private void onMenuItemSelectedOrderModuleByType(MenuItem menuItem) {
        setVideoOderModule(new VideoTypeComparator());
    }

    private final View.OnClickListener mToolBarNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickToolBarNavigation(v);
        }
    };

    private final View.OnClickListener mToolBarPositionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickToolBarPosition(v);
        }
    };

    /**
     * 切换列表样式
     *
     * @param itemMode {@link #VIEW_MODULE_DEFAULT}  默认风格
     *                 {@link #VIEW_MODULE_CARD}     卡片风格
     */
    private void setViewModule(int itemMode) {
        if (mViewModule == itemMode) {
            return;
        }
        mViewModule = itemMode;
        switch (itemMode) {
            case VIEW_MODULE_CARD:
                mAdapter = new VideoCardAdapter(mVideoEntries);
                mViewList.setAdapter(mAdapter);
                mLayoutManager.setSpanCount(2);
                break;
            default:
                mAdapter = new VideoSimpleAdapter(mVideoEntries);
                mViewList.setAdapter(mAdapter);
                mLayoutManager.setSpanCount(1);
                break;
        }
    }

    /**
     * 根据比较器排列数据
     *
     * @param comparator 比较器
     */
    private void setVideoOderModule(Comparator<? super VideoEntry> comparator) {
        List<VideoEntry> datas = mAdapter.getDatas();
        Collections.sort(datas, comparator);
        mAdapter.notifyDataSetChanged();
    }

    private final PopupMenu.OnMenuItemClickListener mMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_view_module:         // 视图模式
                    onMenuItemSelectedViewModule(menuItem);
                    break;
                case R.id.menu_view_module_list:    // 视图模式 - 列表
                    onMenuItemSelectedViewModuleList(menuItem);
                    break;
                case R.id.menu_view_module_card:    // 视图模式 - 卡表
                    onMenuItemSelectedViewModuleCard(menuItem);
                    break;
                case R.id.menu_order_module:        // 排列模式
                    onMenuItemSelectedOrderModule(menuItem);
                    break;
                case R.id.menu_video_order_name:        // 排列模式 - 名称
                    onMenuItemSelectedOrderModuleByName(menuItem);
                    break;
                case R.id.menu_video_order_duration:    // 排列模式 - 播放时长
                    onMenuItemSelectedOrderModuleByDuration(menuItem);
                    break;
                case R.id.menu_video_order_size:        // 排列模式 - 大小
                    onMenuItemSelectedOrderModuleBySize(menuItem);
                    break;
                case R.id.menu_video_order_type:        // 排列模式 - 类型
                    onMenuItemSelectedOrderModuleByType(menuItem);
                    break;
            }
            return true;
        }
    };
}