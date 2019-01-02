package com.loosu.soplayer.widget.videoview.controller;

public interface IMediaController {
    public long getDuration();

    public long getCurrentPosition();

    public float getBufferPercentage();

    public void seeKTo(int position);

    public boolean isPlaying();

    public void pause();

    public void resume();
}
