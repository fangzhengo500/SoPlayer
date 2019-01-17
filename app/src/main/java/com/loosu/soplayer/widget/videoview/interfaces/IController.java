package com.loosu.soplayer.widget.videoview.interfaces;


public interface IController {
    public static final long SHOW_AUTO_HIDE_DEFAULT = 5000;
    public static final long SHOW_AND_NEVER_HIDE = -1;

    public void setEnabled(boolean enable);

    public boolean isShowing();

    public void show(long duration);

    public void hide();

    public void attachMediaPlayer(IMediaController soVideoView);

    public void detachedMediaPLayer();
}
