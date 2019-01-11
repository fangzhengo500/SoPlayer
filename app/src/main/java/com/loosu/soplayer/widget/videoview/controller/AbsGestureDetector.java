package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.graphics.RectF;
import android.media.AudioManager;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.loosu.soplayer.utils.KLog;


public class AbsGestureDetector extends GestureController.Detector implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "AbsGestureDetector";
    
    protected final Context mContext;

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
        KLog.e(TAG, "e = " + e);
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
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }
}
