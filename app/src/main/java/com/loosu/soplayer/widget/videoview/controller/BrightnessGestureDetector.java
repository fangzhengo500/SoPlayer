package com.loosu.soplayer.widget.videoview.controller;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class BrightnessGestureDetector extends AbsGestureDetector {

    private boolean mHandling = false;
    private float mScreenBrightness;

    public BrightnessGestureDetector(Context context, GestureController controller) {
        super(context, controller);
    }

    @Override
    public void onControllerSizeChanged(int w, int h, int oldw, int oldh) {
        mTriggerRect.set(0, 0, w / 2, h);
        mControlRect.set(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mHandling = false;
                mController.hideBrightChange();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        super.onDown(e);
        WindowManager.LayoutParams windowParams = ((Activity) mContext).getWindow().getAttributes();
        mScreenBrightness = windowParams.screenBrightness;
        if (mScreenBrightness == -1) {
            mScreenBrightness = getSysScreenBrightnessInFloat(mScreenBrightness);
        }
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
            if (yDiff > xDiff && mTriggerRect.contains(downEventX, downEventY)) {
                mHandling = true;
            }
        } else {
            float changeBright = (float) (-moveY * 0.002);

            Window window = ((Activity) mContext).getWindow();
            WindowManager.LayoutParams params = window.getAttributes();

            params.screenBrightness = mScreenBrightness + changeBright;
            if (params.screenBrightness > 1) {
                params.screenBrightness = 1;
            } else if (params.screenBrightness < 0) {
                params.screenBrightness = 0;
            }
            window.setAttributes(params);

            mController.showBrightChange(params.screenBrightness);
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * @param defaultBrightness
     * @return [0 ~ 1]
     */
    private float getSysScreenBrightnessInFloat(float defaultBrightness) {
        float result = defaultBrightness;
        try {
            int sysBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            result = sysBrightness * 1f / 255;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
