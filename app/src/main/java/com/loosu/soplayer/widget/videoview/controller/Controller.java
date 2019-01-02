package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.loosu.soplayer.R;

public class Controller extends FrameLayout {
    public Controller(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_controller, this, true);
    }
}
