package com.loosu.soplayer.widget.videoview.detector;

import android.content.Context;
import android.view.MotionEvent;

import com.loosu.soplayer.widget.videoview.controller.gesture.GestureController;


public class SeekGestureDetector extends AbsGestureDetector {

    private long mSeek;

    public SeekGestureDetector(Context context, GestureController controller) {
        super(context, controller);
    }

    @Override
    public void onControllerSizeChanged(int w, int h, int oldw, int oldh) {
        mTriggerRect.set(0, 0, w, h);
        mControlRect.set(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mHandling = false;
                mController.hideSeekChange();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        super.onDown(e);
        mSeek = mController.getCurrentPosition();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
        float downEventX = downEvent.getX();
        float downEventY = downEvent.getY();

        float moveX = event.getX() - downEventX;
        float moveY = event.getY() - downEventY;

        float xDiff = Math.abs(moveX);
        float yDiff = Math.abs(moveY);

        if (!mHandling) {
            if (yDiff < xDiff) {
                mHandling = true;
            }
        } else {
            long duration = mController.getDuration();

            int controllerWidth = mController.getWidth();
            int dSeek = 0;
            if (controllerWidth != 0) {
                dSeek = (int) (duration / controllerWidth * moveX * 1.5);
            }
            long seekTo = mSeek + dSeek;
            seekTo = Math.max(0, Math.min(duration, seekTo));
            mController.showSeekChange(seekTo, duration);
        }

        return true;
    }
}
