package com.loosu.soplayer.widget.videoview.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loosu.soplayer.R;
import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.utils.TimeUtil;
import com.loosu.soplayer.widget.SoProgressBar;

import java.util.ArrayList;
import java.util.List;

public class GestureController extends Controller {
    private static final String TAG = "GestureController";

    private boolean mVolChanging = false;
    private boolean mScreenBrightChanging = false;
    private boolean mSeek = false;

    private int mVolume;
    private float mScreenBrightness;
    private long mCurrentPosition;

    private SoProgressBar mProgressVolume;
    private SoProgressBar mProgressScreenBright;

    private View mLayoutSeek;
    private TextView mTvSeekPosition;
    private TextView mTvSeekDuration;
    private ProgressBar mProgressBarSeek;

    private final android.view.GestureDetector mGestureDetector;

    private final List<Detector> mDetectors = new ArrayList<>();

    private int mSeekTo = -1;

    public GestureController(@NonNull Context context) {
        super(context);
        mGestureDetector = new android.view.GestureDetector(context, mGestureListener);
        mGestureDetector.setOnDoubleTapListener(mOnDoubleTapListener);
        mDetectors.add(new VolumeGestureDetector(getContext(), this));
        mDetectors.add(new BrightnessGestureDetector(getContext(), this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.widget_gesture_controller;
    }

    @Override
    protected void initController(@NonNull Context context) {
        super.initController(context);
        mProgressVolume = findViewById(R.id.progress_volume);
        mProgressScreenBright = findViewById(R.id.progress_screen_brightness);

        mLayoutSeek = findViewById(R.id.layout_seek);
        mTvSeekPosition = findViewById(R.id.tv_seek_position);
        mTvSeekDuration = findViewById(R.id.tv_seek_duration);
        mProgressBarSeek = findViewById(R.id.progress_bar_seek);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (Detector detector : mDetectors) {
            detector.onControllerSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //List<Detector> handlingDetectors = findHandlingDetectors();
        for (int i = 0; i < mDetectors.size(); i++) {
            mDetectors.get(i).onTouchEvent(event);
        }
        return true;
//        if (mPlayer != null && mPlayer.isPlaying()) {
//            final int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_UP:
//                    mVolChanging = false;
//                    mScreenBrightChanging = false;
//                    mSeek = false;
//                    mProgressVolume.setVisibility(GONE);
//                    mProgressScreenBright.setVisibility(GONE);
//                    mLayoutSeek.setVisibility(GONE);
//                    if (mSeekTo != -1) {
//                        mPlayer.seeKTo(mSeekTo);
//                        mSeekTo = -1;
//                    }
//                    break;
//            }
//            return mGestureDetector.onTouchEvent(event);
//        } else {
//            return super.onTouchEvent(event);
//        }
    }

    public void showBrightChange(float present) {
        mProgressScreenBright.setVisibility(VISIBLE);
        mProgressScreenBright.setProgress((int) (mProgressScreenBright.getMax() * present));
        mProgressScreenBright.setText(String.format("%.1f", present));
    }

    public void hideBrightChange() {
        mProgressScreenBright.setVisibility(GONE);
    }

    public void showVolumeChange(float present) {
        mProgressVolume.setVisibility(VISIBLE);
        mProgressVolume.setProgress((int) (mProgressVolume.getMax() * present));
        mProgressVolume.setText(String.format("%.1f", present));
    }

    public void hideVolumeChange() {
        mProgressVolume.setVisibility(GONE);
    }

    /**
     * @param defaultBrightness
     * @return [0 ~ 1]
     */
    private float getSysScreenBrightnessInFloat(float defaultBrightness) {
        float result = defaultBrightness;
        try {
            int sysBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            result = sysBrightness * 1f / 255;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private android.view.GestureDetector.OnGestureListener mGestureListener = new android.view.GestureDetector.OnGestureListener() {
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
            float downX = downEvent.getX();

            float moveX = event.getX() - downEvent.getX();
            float moveY = event.getY() - downEvent.getY();

            float xDiff = Math.abs(moveX);
            float yDiff = Math.abs(moveY);

            if (!mVolChanging && !mScreenBrightChanging && !mSeek) {
                if (yDiff > xDiff) {
                    if (downX > getWidth() / 2) {
                        mVolChanging = true;
                        mProgressVolume.setVisibility(VISIBLE);
                        AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                        mVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    } else {
                        mScreenBrightChanging = true;
                        mProgressScreenBright.setVisibility(VISIBLE);
                        if (getContext() instanceof Activity) {
                            WindowManager.LayoutParams windowParams = ((Activity) getContext()).getWindow().getAttributes();
                            mScreenBrightness = windowParams.screenBrightness;
                            if (mScreenBrightness == -1) {
                                mScreenBrightness = getSysScreenBrightnessInFloat(mScreenBrightness);
                            }
                        }
                    }
                } else {
                    mSeek = true;
                    mCurrentPosition = mPlayer.getCurrentPosition();
                    mLayoutSeek.setVisibility(VISIBLE);
                }
            } else if (mVolChanging) {
                int dVol = (int) (-moveY * 0.02);

                int streamType = AudioManager.STREAM_MUSIC;
                AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(streamType, mVolume + dVol, 0);

                int volume = am.getStreamVolume(streamType);
                int maxVolume = am.getStreamMaxVolume(streamType);

                mProgressVolume.setProgress((int) (volume * 1f / maxVolume * mProgressVolume.getMax()));

            } else if (mScreenBrightChanging) {
                if (getContext() instanceof Activity) {

                    float changeBright = (float) (-moveY * 0.002);

                    Window window = ((Activity) getContext()).getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();

                    params.screenBrightness = mScreenBrightness + changeBright;
                    if (params.screenBrightness > 1) {
                        params.screenBrightness = 1;
                    } else if (params.screenBrightness < 0) {
                        params.screenBrightness = 0;
                    }
                    window.setAttributes(params);

                    mProgressScreenBright.setProgress((int) (params.screenBrightness * mProgressScreenBright.getMax()));
                    mProgressScreenBright.setText(String.format("%.1f", params.screenBrightness));
                }
            } else if (mSeek) {
                long duration = mPlayer.getDuration();


                int controllerWidth = getWidth();
                int dSeek = 0;
                if (controllerWidth != 0) {
                    dSeek = (int) (duration / controllerWidth * moveX * 1.5);
                }
                long seekTo = mCurrentPosition + dSeek;
                seekTo = Math.max(0, Math.min(duration, seekTo));
                float present = 0;
                if (duration != 0) {
                    present = seekTo * 1f / duration;
                }

                mSeekTo = (int) seekTo;

                mTvSeekPosition.setText(TimeUtil.formatDuration(seekTo));
                mTvSeekDuration.setText(TimeUtil.formatDuration(duration));
                mProgressBarSeek.setProgress((int) (present * mProgressBarSeek.getMax()));
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

    private android.view.GestureDetector.OnDoubleTapListener mOnDoubleTapListener = new android.view.GestureDetector.OnDoubleTapListener() {
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

    public abstract static class Detector {
        protected final GestureController mController;

        public Detector(GestureController controller) {
            mController = controller;
        }

        public abstract void onControllerSizeChanged(int w, int h, int oldw, int oldh);

        public abstract boolean onTouchEvent(MotionEvent event);
    }
}
