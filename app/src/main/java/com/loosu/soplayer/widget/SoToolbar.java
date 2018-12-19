package com.loosu.soplayer.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.loosu.soplayer.R;

public class SoToolbar extends FrameLayout {

    private ImageView mBtnSoToolbarPosition;
    private ImageView mBtnSoToolbarNavigation;
    private TextView mTvSoToolbarTitle;

    public SoToolbar(@NonNull Context context) {
        this(context, null);
    }

    public SoToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.widget_so_toolbar, this, true);
        findView(context, attrs, defStyleAttr);
    }

    private void findView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mBtnSoToolbarPosition = findViewById(R.id.so_toolbar_btn_position);
        mBtnSoToolbarNavigation = findViewById(R.id.so_toolbar_btn_nagtion);
        mTvSoToolbarTitle = findViewById(R.id.so_toolbar_title);
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        mBtnSoToolbarNavigation.setImageResource(resId);
    }

    public void setNavigationBackgroundColor(@ColorInt int color) {
        mBtnSoToolbarNavigation.setBackgroundColor(color);
    }

    public void setNavigationBackgroundResource(@DrawableRes int resId) {
        mBtnSoToolbarNavigation.setBackgroundResource(resId);
    }
    public void setPositionIcon(@DrawableRes int resId) {
        mBtnSoToolbarPosition.setImageResource(resId);
    }

    public void setPositionIconBackgroundColor(@ColorInt int color) {
        mBtnSoToolbarPosition.setBackgroundColor(color);
    }

    public void setTitle(@StringRes int resId) {
        mTvSoToolbarTitle.setText(resId);
    }

    public void setTitle(CharSequence text) {
        mTvSoToolbarTitle.setText(text);
    }

    public void setNavigationClickListener(OnClickListener listener) {
        mBtnSoToolbarNavigation.setOnClickListener(listener);
    }
}
