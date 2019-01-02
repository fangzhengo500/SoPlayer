package com.loosu.soplayer.widget.videoview.listener;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public interface IjkMediaPlayerComponentListener extends IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnTimedTextListener,
        IMediaPlayer.OnVideoSizeChangedListener {
}
