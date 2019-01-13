package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.loosu.soplayer.utils.KLog;
import com.loosu.soplayer.widget.videoview.interfaces.IController;
import com.loosu.soplayer.widget.videoview.interfaces.IMediaController;


public class Controller extends FrameLayout implements IController {
    private static final String TAG = "AbsSoVideoView";

    protected IMediaController mPlayer;
    protected boolean mShowing = true;


    public Controller(@NonNull Context context) {
        super(context);
    }

    public void setMediaPlayer(IMediaController player) {
        mPlayer = player;
    }

    @Override
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    public void show() {
        mShowing = true;
    }

    @Override
    public void hide() {
        mShowing = false;
    }

    public void startOrPausePlayer() {
        if (mPlayer.getState() == IMediaController.State.STARTED) {
            KLog.d(TAG, "暂停 - pause");
            mPlayer.pause();

        } else if (mPlayer.getState() == IMediaController.State.PAUSED || mPlayer.getState() == IMediaController.State.PLAYBACK_COMPLETED) {
            KLog.d(TAG, "恢复 - resume");
            mPlayer.resume();

        } else {
            mPlayer.start();

        }
    }
}
