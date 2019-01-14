package com.loosu.test.videoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import com.loosu.soplayer.widget.videoview.SoVideoView;

public class VerticalHandlerVideoView extends SoVideoView {
    private static final String TAG = "VerticalHandlerVideoVie";

    private float mDownX;
    private float mDownY;
    private MotionEvent mDownEvent;
    private int mTouchSlop;

    public VerticalHandlerVideoView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalHandlerVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalHandlerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                requestParentDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mDownX;
                float dy = ev.getY() - mDownY;
                if (Math.abs(dx) < Math.abs(dy) && Math.abs(dy) > mTouchSlop) {
                    // 垂直滑动
                    requestParentDisallowInterceptTouchEvent(false);

                } else {
                    // 水平滑动
                    requestParentDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getParent() != null) {
                    requestParentDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownEvent = MotionEvent.obtain(ev);
                break;
            default:
                float x = ev.getX();
                float y = ev.getY();

                int width = getWidth();
                if (mDownX < width / 5 || mDownX > width * 4 / 5) {
                    requestParentDisallowInterceptTouchEvent(true);
                } else if (mDownY > getHeight() * 4 / 5) {
                    requestParentDisallowInterceptTouchEvent(true);
                } else {
                    requestParentDisallowInterceptTouchEvent(false);
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }


    public void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }
}
