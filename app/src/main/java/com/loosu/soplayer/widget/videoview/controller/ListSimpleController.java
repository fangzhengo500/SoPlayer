package com.loosu.soplayer.widget.videoview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.loosu.soplayer.R;
import com.loosu.soplayer.widget.videoview.controller.gesture.AbsGestureController;
import com.loosu.soplayer.widget.videoview.detector.ClickDetector;

public class ListSimpleController extends AbsGestureController {
    public ListSimpleController(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_list_simple, this, true);
        initController(context);
    }

    @Override
    public void updateBtnPlay() {

    }

    private void initController(Context context) {
        addGestureDetector(new ClickDetector(context, this));
    }
}
