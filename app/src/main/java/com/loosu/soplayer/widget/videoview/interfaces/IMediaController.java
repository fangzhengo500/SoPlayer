package com.loosu.soplayer.widget.videoview.interfaces;

public interface IMediaController {
    public String getDataSource();

    public long getDuration();

    public long getCurrentPosition();

    public float getBufferPercentage();

    public void seeKTo(int position);

    public boolean isPlaying();

    public void start();

    public void stop();

    public void pause();

    public void resume();

    public State getState();

    enum State {
        IDLE,
        INITIALIZED,
        PREPARING,
        PREPARED,
        STARTED,
        PAUSED,
        PLAYBACK_COMPLETED,
        STOPPED,
        ERROR,
        END,
    }
}
