package com.loosu.soplayer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loosu.soplayer.R;

public class SoProgressBar extends LinearLayout {

    private ImageView mIvIcon;
    private ProgressBar mProgressBar;
    private TextView mTvText;

    public SoProgressBar(Context context) {
        this(context, null);
    }

    public SoProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_so_progress_bar, this, true);
        mIvIcon = findViewById(R.id.iv_icon);
        mProgressBar = findViewById(R.id.progress_bar);
        mTvText = findViewById(R.id.tv_text);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SoProgressBar);
        Drawable iconDrawable = a.getDrawable(R.styleable.SoProgressBar_so_progress_bar_icon);
        a.recycle();

        mIvIcon.setImageDrawable(iconDrawable);
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    public int getProgress() {
        return mProgressBar.getProgress();
    }

    public void setMax(int max) {
        mProgressBar.setMax(max);
    }

    public int getMax() {
        return mProgressBar.getMax();
    }

    public void setText(String text) {
        mTvText.setText(text);
    }
}
