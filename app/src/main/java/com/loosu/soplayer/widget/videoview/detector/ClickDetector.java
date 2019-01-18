package com.loosu.soplayer.widget.videoview.detector;

import android.content.Context;
import android.view.MotionEvent;

import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.controller.gesture.GestureController;
import com.loosu.soplayer.widget.videoview.interfaces.IController;


public class ClickDetector extends AbsGestureDetector<GestureController> {
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
            mController.show(IController.SHOW_AUTO_HIDE_DEFAULT);
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        KLog.e(TAG, "");
        mController.startOrPausePlayer();
        mController.updateBtnPlay();
        return true;
    }
}
