package com.loosu.soplayer.widget.videoview;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

public interface IVideoView {
    public void setDataSource(Context context, Uri uri) throws IOException;

    public void setDataSource(String path) throws IOException;

    public String getDataSource();

    public void start();

    public void resume();

    public void pause();

    public void release();
}
