package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.SoProgressBar;

public class GestureController extends Controller {
    private static final String TAG = "GestureController";

    private boolean mVolChanging = false;
    private int mVolume;

    private SoProgressBar mProgressVolume;

    private final GestureDetector mGestureDetector;

    public GestureController(@NonNull Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, mGestureListener);
        mGestureDetector.setOnDoubleTapListener(mOnDoubleTapListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.widget_gesture_controller;
    }

    @Override
    protected void initController(@NonNull Context context) {
        super.initController(context);
        mProgressVolume = findViewById(R.id.progress_volume);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mVolChanging = false;
                mProgressVolume.setVisibility(GONE);
                break;
        }
        return mGestureDetector.onTouchEvent(event);
    }


    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            KLog.d(TAG, "e = " + e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            KLog.d(TAG, "e = " + e);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
            float moveX = event.getX() - downEvent.getX();
            float moveY = event.getY() - downEvent.getY();

            float xDiff = Math.abs(moveX);
            float yDiff = Math.abs(moveY);

            if (!mVolChanging) {
                if (yDiff > xDiff) {
                    mVolChanging = true;
                    mProgressVolume.setVisibility(VISIBLE);
                    AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                    mVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                }
            } else {
                int dVol = (int) (-moveY * 0.02);

                int streamType = AudioManager.STREAM_MUSIC;
                AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(streamType, mVolume + dVol, 0);

                int volume = am.getStreamVolume(streamType);
                int maxVolume = am.getStreamMaxVolume(streamType);

                mProgressVolume.setProgress((int) (volume * 1f / maxVolume * mProgressVolume.getMax()));
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            KLog.d(TAG, "e = " + e);
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent event, float velocityX, float velocityY) {
            KLog.d(TAG, "velocityX = " + velocityX + ", velocityY = " + velocityY);
            return true;
        }
    };

    private GestureDetector.OnDoubleTapListener mOnDoubleTapListener = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            KLog.i(TAG, "e = " + e);
            if (isShowing()) {
                hide();
            } else {
                show();
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            KLog.i(TAG, "e = " + e);
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            KLog.i(TAG, "e = " + e);
            return true;
        }
    };
}
