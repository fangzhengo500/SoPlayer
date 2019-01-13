package com.loosu.soplayer.widget.videoview.detector;

import android.content.Context;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.loosu.soplayer.widget.videoview.controller.GestureController;


public abstract class AbsGestureDetector extends GestureController.Detector implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "AbsGestureDetector";

    protected final Context mContext;

    protected boolean mHandling = false;
    protected final RectF mTriggerRect = new RectF();
    protected final RectF mControlRect = new RectF();
    private final GestureDetector mDetector;

    public AbsGestureDetector(Context context, GestureController controller) {
        super(controller);
        mContext = context;
        mDetector = new GestureDetector(context, this);
        mDetector.setOnDoubleTapListener(this);
    }

    @Override
    public void onControllerSizeChanged(int w, int h, int oldw, int oldh) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    public boolean isHandling() {
        return mHandling;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent event, float velocityX, float velocityY) {
        return true;
    }
}
