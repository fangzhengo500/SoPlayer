package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;

public class GestureController extends Controller {
    private static final String TAG = "GestureController";

    private final int mTouchSlop;

    private float mLastMotionX;
    private float mLastMotionY;

    private View mLayoutVolumeChange;

    public GestureController(@NonNull Context context) {
        super(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.widget_gesture_controller;
    }

    @Override
    protected void initController(@NonNull Context context) {
        super.initController(context);
        mLayoutVolumeChange = findViewById(R.id.layout_volume_change);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = event.getX();
                mLastMotionY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                float dx = x - mLastMotionX;
                float dy = y - mLastMotionY;

                float xDiff = Math.abs(dx);
                float yDiff = Math.abs(dy);

                mLastMotionX = x;
                mLastMotionY = y;

                showVolumeChange();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                hideVolumeChange();
                break;
        }
        return true;
    }

    private void showVolumeChange() {
        KLog.d(TAG, "");
        mLayoutVolumeChange.setVisibility(VISIBLE);

        ProgressBar progress_volume = mLayoutVolumeChange.findViewById(R.id.progress_volume);
        TextView tv_volume = mLayoutVolumeChange.findViewById(R.id.tv_volume);
    }

    private void hideVolumeChange() {
        KLog.d(TAG, "");
        mLayoutVolumeChange.setVisibility(GONE);
    }
}
