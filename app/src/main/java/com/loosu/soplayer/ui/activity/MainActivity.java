package com.loosu.soplayer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.loosu.soplayer.R;
import com.loosu.test.ijk.IjkMediaPlayerTestActivity;

public class MainActivity extends AppCompatActivity {


    private View mBtnMediaExplorer;
    private View mBtnBrowser;
    private View mBtnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView(savedInstanceState);
        initListener(savedInstanceState);
    }

    private void findView(Bundle savedInstanceState) {
        mBtnMediaExplorer = findViewById(R.id.btn_media_explorer);
        mBtnBrowser = findViewById(R.id.btn_web_browser);
        mBtnSettings = findViewById(R.id.btn_settings);
    }

    private void initListener(Bundle savedInstanceState) {
        mBtnMediaExplorer.setOnClickListener(mOnClickListener);
        mBtnBrowser.setOnClickListener(mOnClickListener);
        mBtnSettings.setOnClickListener(mOnClickListener);
    }

    private void onClickBtnMediaExplorer() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return;
        }
        Intent intent = MediaExplorerActivity.getStartIntent(this, MediaExplorerActivity.PAGE_DOCUMENT);
        startActivity(intent);
    }

    private void onClickWebBrowser() {
        Intent intent = MediaExplorerActivity.getStartIntent(this, MediaExplorerActivity.PAGE_BROWSER);
        startActivity(intent);
    }

    private void onClickBtnSettings() {
        //Intent intent = new Intent(this, IjkMediaPlayerTestActivity.class);
        //startActivity(intent);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_media_explorer:
                    onClickBtnMediaExplorer();
                    break;
                case R.id.btn_web_browser:
                    onClickWebBrowser();
                    break;
                case R.id.btn_settings:
                    onClickBtnSettings();
                    break;
            }
        }
    };


}
