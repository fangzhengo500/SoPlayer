package com.loosu.soplayer.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.soplayer.ui.fragment.DocumentsFragment;
import com.loosu.soplayer.ui.fragment.WebBrowserFragment;
import com.loosu.soplayer.utils.SystemUiUtil;

public class MediaExplorerActivity extends AppCompatActivity implements View.OnClickListener {

    private View mLayoutMenu;
    private View mBtnFolder;
    private View mBtnNet;
    private View mBtnSettings;

    private DocumentsFragment mDocumentsFragment;
    private WebBrowserFragment mWebBrowserFragment;

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
        mDocumentsFragment = new DocumentsFragment();
        mWebBrowserFragment = new WebBrowserFragment();
    }

    private void findView(Bundle savedInstanceState) {
        mLayoutMenu = findViewById(R.id.layout_menu);

        mBtnFolder = findViewById(R.id.btn_folder);
        mBtnNet = findViewById(R.id.btn_net);
        mBtnSettings = findViewById(R.id.btn_settings);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment_container, mDocumentsFragment)
                .commit();
    }

    private void initView(Bundle savedInstanceState) {
        final Context context = this;

        mLayoutMenu.setPadding(
                mLayoutMenu.getPaddingLeft(),
                mLayoutMenu.getPaddingTop() + SystemUiUtil.getStatusBarHeight(context),
                mLayoutMenu.getPaddingRight(),
                mLayoutMenu.getPaddingBottom());
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment_container, mDocumentsFragment)
                .commit();
    }

    private void onClickBtnNet() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment_container, mWebBrowserFragment)
                .commit();
    }

    private void onClickBtnSettings() {

    }
}
