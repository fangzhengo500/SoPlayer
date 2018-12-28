package com.loosu.soplayer.domain;

public enum PlayerState {
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
