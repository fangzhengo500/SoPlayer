package com.loosu.soplayer.domain;

import android.database.Cursor;
import android.provider.MediaStore;

public class VideoEntry {

    private String mData;
    private String mDataAdded;
    private String mDataModified;
    private String mDisplayName;
    private String mHeight;
    private String mWidth;
    private String mMimeType;
    private String mSize;
    private String mTitle;
    private String mDuration;

    public VideoEntry(Cursor cursor) {
        if (cursor == null) {
            return;
        }

        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
        mDataAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED));
        mDataModified = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_MODIFIED));
        mDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));
        mHeight = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT));
        mWidth = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH));
        mMimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE));
        mSize = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE));
        mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE));
        mDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
    }

    @Override
    public String toString() {
        return new StringBuilder().append("VideoEntry{")
                .append("mData='").append(mData).append('\n')
                .append(" mDataAdded='").append(mDataAdded).append('\n')
                .append(" mDataModified='").append(mDataModified).append('\n')
                .append(" mDisplayName='").append(mDisplayName).append('\n')
                .append(" mHeight='").append(mHeight).append('\n')
                .append(" mWidth='").append(mWidth).append('\n')
                .append(" mMimeType='").append(mMimeType).append('\n')
                .append(" mSize='").append(mSize).append('\n')
                .append(" mTitle='").append(mTitle).append('\n')
                .append(" mDuration='").append(mDuration).append('\n')
                .append('}')
                .toString();
    }
}
