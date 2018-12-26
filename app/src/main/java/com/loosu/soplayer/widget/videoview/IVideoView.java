package com.loosu.soplayer.widget.videoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

public interface IVideoView {
    void setDataSource(Context context, Uri uri) throws IOException;

    @TargetApi(14)
    void setDataSource(Context context, Uri uri, Map<String, String> var3) throws IOException;

    void setDataSource(FileDescriptor fd) throws IOException;

    void setDataSource(String path) throws IOException;

    String getDataSource();
}
