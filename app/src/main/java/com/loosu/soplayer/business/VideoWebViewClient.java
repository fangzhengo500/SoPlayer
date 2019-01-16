package com.loosu.soplayer.business;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.loosu.soplayer.utils.KLog;

import java.util.ArrayList;
import java.util.List;


public class VideoWebViewClient extends WebViewClient {
    private static final String TAG = "VideoWebViewClient";

    private final List<String> mVideoUrls = new ArrayList<>();

    public List<String> getVideoUrls() {
        return new ArrayList<>(mVideoUrls);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        KLog.i(TAG, "url = " + url);
        mVideoUrls.clear();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        KLog.w(TAG, "url = " + url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        KLog.d(TAG, "url = " + url);
        if (!TextUtils.isEmpty(url)) {
            String lowerCase = url.toLowerCase();
            if (lowerCase.endsWith(".mp4") || lowerCase.endsWith(".flv")) {
                mVideoUrls.add(url);
            }
        }
    }
}
