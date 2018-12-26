package com.loosu.soplayer.domain;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

public class VideoEntry implements Parcelable {

    private String mTitle;
    private String mDisplayName;
    private String mData;
    private String mMimeType;
    private long mDuration;
    private long mSize;
    private long mDataAdded;
    private long mDataModified;
    private int mHeight;
    private int mWidth;

    public VideoEntry(Cursor cursor) {
        if (cursor == null) {
            return;
        }

        mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE));
        mDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));
        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
        mMimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE));
        mDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
        mSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE));
        mDataAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_ADDED));
        mDataModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATE_MODIFIED));
        mHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT));
        mWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH));
    }

    protected VideoEntry(Parcel in) {
        mTitle = in.readString();
        mDisplayName = in.readString();
        mData = in.readString();
        mMimeType = in.readString();
        mDuration = in.readLong();
        mSize = in.readLong();
        mDataAdded = in.readLong();
        mDataModified = in.readLong();
        mHeight = in.readInt();
        mWidth = in.readInt();
    }

    public static final Creator<VideoEntry> CREATOR = new Creator<VideoEntry>() {
        @Override
        public VideoEntry createFromParcel(Parcel in) {
            return new VideoEntry(in);
        }

        @Override
        public VideoEntry[] newArray(int size) {
            return new VideoEntry[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(String mimeType) {
        mMimeType = mimeType;
    }

    public long getDataAdded() {
        return mDataAdded;
    }

    public void setDataAdded(long dataAdded) {
        mDataAdded = dataAdded;
    }

    public long getDataModified() {
        return mDataModified;
    }

    public void setDataModified(long dataModified) {
        mDataModified = dataModified;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public long getSize() {
        return mSize;
    }

    public void setSize(long size) {
        mSize = size;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDisplayName);
        dest.writeString(mData);
        dest.writeString(mMimeType);
        dest.writeLong(mDuration);
        dest.writeLong(mSize);
        dest.writeLong(mDataAdded);
        dest.writeLong(mDataModified);
        dest.writeInt(mHeight);
        dest.writeInt(mWidth);
    }
}
