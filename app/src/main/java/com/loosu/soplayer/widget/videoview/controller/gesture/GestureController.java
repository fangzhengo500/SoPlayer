package com.loosu.soplayer.widget.videoview.controller.gesture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.loosu.soplayer.widget.videoview.detector.BrightnessGestureDetector;
import com.loosu.soplayer.widget.videoview.detector.ClickDetector;
import com.loosu.soplayer.widget.videoview.detector.SeekGestureDetector;
import com.loosu.soplayer.widget.videoview.detector.VolumeGestureDetector;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;

import java.util.ArrayList;
import java.util.List;

public class GestureController extends AnimationGestureController {
    private static final String TAG = "GestureController";


    private Detector mHandingDetector = null;
    private final List<Detector> mDetectors = new ArrayList<>();


    public GestureController(@NonNull Context context) {
        super(context);
        mDetectors.add(new VolumeGestureDetector(getContext(), this));
        mDetectors.add(new BrightnessGestureDetector(getContext(), this));
        mDetectors.add(new SeekGestureDetector(getContext(), this));
        mDetectors.add(new ClickDetector(getContext(), this));
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
        if (!isEnabled()) {
            return super.onTouchEvent(event);
        }

        boolean result = false;

        // 目前交互设计, 同时只有一个 Detector 可处理触摸事件.
        final Detector handlingDetector = findHandlingDetector();

        if (handlingDetector != null) {
            // 有 Detector 处理中, 交个该处理器处理
            result = handlingDetector.onTouchEvent(event);
        } else {

            // 没有还没有 Detector 处理, 交给各个处理器处理
            for (Detector detector : mDetectors) {
                if (detector.onTouchEvent(event)) {
                    result = true;
                }
            }
        }

        // 没有处理器处理过事件, 交给 super.onTouchEvent(event) 处理.
        // should never happen.
        if (!result) {
            super.onTouchEvent(event);
        }

        // 触目事件结束 mHandingDetector 制空
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandingDetector = null;
                break;
        }

        return result;
    }

    private Detector findHandlingDetector() {
        if (mHandingDetector != null) {
            return mHandingDetector;
        }

        for (Detector detector : mDetectors) {
            if (detector.isHandling()) {
                mHandingDetector = detector;
            }
        }
        return mHandingDetector;
    }

    @Override
    protected void onClickBtnPlay() {
        super.onClickBtnPlay();
        if (mPlayer == null) {
            return;
        }
        boolean isStartPlay = mPlayer.getState() == IMediaController.State.STARTED ||
                mPlayer.getState() == IMediaController.State.PREPARING ||
                mPlayer.getState() == IMediaController.State.PREPARED;

        if (isStartPlay) {
            hide();
        }
    }
}
