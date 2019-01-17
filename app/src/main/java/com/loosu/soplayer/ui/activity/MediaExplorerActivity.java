package com.loosu.soplayer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.ui.fragment.DocumentsFragment;
import com.loosu.soplayer.ui.fragment.WebBrowserFragment;
import com.loosu.soplayer.utils.SystemUiUtil;

public class MediaExplorerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_PAGE = "KEY_PAGE";

    public static final int PAGE_DOCUMENT = 0;
    public static final int PAGE_BROWSER = 1;

    private View mLayoutMenu;
    private View mBtnFolder;
    private View mBtnNet;
    private View mBtnSettings;

    private SparseArray<Fragment> mFragmentPages = new SparseArray<>();

    /**
     * 获取配置好的启动intent
     *
     * @param context context
     * @param page    {@link #PAGE_DOCUMENT}, {@link #PAGE_DOCUMENT}
     */
    public static Intent getStartIntent(Context context, int page) {
        Intent intent = new Intent(context, MediaExplorerActivity.class);
        intent.putExtra(KEY_PAGE, page);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_explorer);
        init(savedInstanceState);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mFragmentPages.put(PAGE_DOCUMENT, new DocumentsFragment());
        mFragmentPages.put(PAGE_BROWSER, new WebBrowserFragment());
    }

    private void findView(Bundle savedInstanceState) {
        mLayoutMenu = findViewById(R.id.layout_menu);

        mBtnFolder = findViewById(R.id.btn_folder);
        mBtnNet = findViewById(R.id.btn_net);
        mBtnSettings = findViewById(R.id.btn_settings);
    }

    private void initView(Bundle savedInstanceState) {
        final Context context = this;

        mLayoutMenu.setPadding(
                mLayoutMenu.getPaddingLeft(),
                mLayoutMenu.getPaddingTop() + SystemUiUtil.getStatusBarHeight(context),
                mLayoutMenu.getPaddingRight(),
                mLayoutMenu.getPaddingBottom());


        int page = getIntent().getIntExtra(KEY_PAGE, PAGE_DOCUMENT);
        switchToPage(page);
    }

    private void initListener(Bundle savedInstanceState) {
        mBtnFolder.setOnClickListener(this);
        mBtnNet.setOnClickListener(this);
        mBtnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_folder:
                onClickBtnFolder();
                break;
            case R.id.btn_net:
                onClickBtnNet();
                break;
            case R.id.btn_settings:
                onClickBtnSettings();
                break;
        }
    }

    private void onClickBtnFolder() {
        switchToPage(PAGE_DOCUMENT);
    }

    private void onClickBtnNet() {
        switchToPage(PAGE_BROWSER);
    }

    private void onClickBtnSettings() {

    }

    private void switchToPage(int page) {
        Fragment fragment = mFragmentPages.get(page);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment_container, fragment)
                .commit();
    }
}
