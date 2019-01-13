package com.loosu.soplayer.widget.videoview.detector;

import android.content.Context;
import android.view.MotionEvent;

import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.controller.GestureController;


public class ClickDetector extends AbsGestureDetector {
    private static final String TAG = "ClickDetector";

    public ClickDetector(Context context, GestureController controller) {
        super(context, controller);
    }

    @Override
    public void onControllerSizeChanged(int w, int h, int oldw, int oldh) {
        mTriggerRect.set(0, 0, w, h);
        mControlRect.set(0, 0, w, h);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        KLog.w(TAG, "");
        if (mController.isShowing()) {
            mController.hide();
        } else {
            mController.show();
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        KLog.e(TAG, "");
        mController.startOrPausePlayer();
        return true;
    }
}