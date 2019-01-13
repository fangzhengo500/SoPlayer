package com.loosu.soplayer.widget.videoview.detector;

import android.content.Context;
import android.media.AudioManager;
import android.view.MotionEvent;

import com.loosu.soplayer.widget.videoview.controller.gesture.GestureController;


public class VolumeGestureDetector extends AbsGestureDetector {

    private int mVolume;

    public VolumeGestureDetector(Context context, GestureController controller) {
        super(context, controller);
    }

    @Override
    public void onControllerSizeChanged(int w, int h, int oldw, int oldh) {
        mTriggerRect.set(w / 2, 0, w, h);
        mControlRect.set(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mHandling = false;
                mController.hideVolumeChange();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        super.onDown(e);
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
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
            int dVol = (int) (-moveY * 0.02);

            int streamType = AudioManager.STREAM_MUSIC;
            AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(streamType, mVolume + dVol, 0);

            int volume = am.getStreamVolume(streamType);
            int maxVolume = am.getStreamMaxVolume(streamType);

            mController.showVolumeChange(volume * 1f / maxVolume);
        }

        return true;
    }
}
