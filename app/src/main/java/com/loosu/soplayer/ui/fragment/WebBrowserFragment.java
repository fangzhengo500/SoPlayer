package com.loosu.soplayer.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.SystemUiUtil;

public class WebBrowserFragment extends Fragment {
    private static final String TAG = "WebBrowserFragment";

    private View mToolbar;
    private EditText mEtInput;
    private View mBtnToolbarClear;

    private WebView mWebView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_browser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view, savedInstanceState);
        initView(view, savedInstanceState);
        initListener(view, savedInstanceState);
    }

    private void findView(View view, Bundle savedInstanceState) {
        // toolbar
        mToolbar = view.findViewById(R.id.toolbar);
        mEtInput = view.findViewById(R.id.et_input);
        mBtnToolbarClear = view.findViewById(R.id.btn_toolbar_clear);

        mWebView = view.findViewById(R.id.web_view);
    }

    private void initView(View view, Bundle savedInstanceState) {
        final Context context = getContext();

        // toolbar
        mToolbar.setPadding(mToolbar.getPaddingLeft(),
                mToolbar.getPaddingTop() + SystemUiUtil.getStatusBarHeight(context),
                mToolbar.getPaddingRight(),
                mToolbar.getPaddingBottom());
        mBtnToolbarClear.setOnClickListener(mOnClickListener);

       mWebView.setWebViewClient(mWebViewClient);
    }

    private void initListener(View view, Bundle savedInstanceState) {

        mEtInput.setOnEditorActionListener(mEditorActionListener);
    }

    private void onClickBtnToolbarClear(View v) {
        mEtInput.setText(null);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_toolbar_clear:
                    onClickBtnToolbarClear(v);
                    break;
            }
        }
    };

    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            KLog.d(TAG, "actionId = " + actionId + ", event = " + event);
            switch (actionId) {
                case EditorInfo.IME_ACTION_GO:
                    onEditorActionGo(v);
                    break;
            }
            return false;
        }
    };

    private void onEditorActionGo(TextView v) {
        String inputStr = v.getText().toString();
        if (TextUtils.isEmpty(inputStr)) {
            return;
        }

        Uri uri = Uri.parse(inputStr);
        String scheme = uri.getScheme();

        if (TextUtils.isEmpty(scheme)) {
            mWebView.loadUrl("https://" + inputStr);
        } else {
            mWebView.loadUrl(inputStr);
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            KLog.d(TAG, "view = " + view + ", url = " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            KLog.d(TAG, "view = " + view + ", url = " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            KLog.d(TAG, "request = " + request);
            KLog.d(TAG, "url = " + request.getUrl());
            KLog.d(TAG, "headers = " + request.getRequestHeaders());
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    };


}
