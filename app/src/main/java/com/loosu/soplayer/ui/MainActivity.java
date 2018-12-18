package com.loosu.soplayer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.loosu.soplayer.R;

public class MainActivity extends AppCompatActivity {


    private View mBtnMediaExplorer;
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
        mBtnSettings = findViewById(R.id.btn_settings);
    }

    private void initListener(Bundle savedInstanceState) {
        mBtnMediaExplorer.setOnClickListener(mOnClickListener);
        mBtnSettings.setOnClickListener(mOnClickListener);
    }

    private void onClickBtnMediaExplorer() {
        Intent intent = new Intent(this, MediaExplorerActivity.class);
        startActivity(intent);
    }

    private void onClickBtnSettings() {

    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_media_explorer:
                    onClickBtnMediaExplorer();
                    break;
                case R.id.btn_settings:
                    onClickBtnSettings();
                    break;
            }
        }
    };

}
