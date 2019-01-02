package com.loosu.soplayer.widget.videoview.listener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class IjkMediaPlayerComponentListenerAdapter implements IjkMediaPlayerComponentListener {
    @Override
    public void onBufferingUpdate(IMediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(IMediaPlayer mp) {

    }

    @Override
    public boolean onError(IMediaPlayer mp,int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp,  int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(IMediaPlayer mp) {

    }

    @Override
    public void onTimedText(IMediaPlayer mp, IjkTimedText text) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height,
                                   int sar_num, int sar_den) {

    }
}
