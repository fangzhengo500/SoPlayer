package com.loosu.soplayer.widget.popu;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

public class DocumentToolBarPopu extends PopupMenu {
    public DocumentToolBarPopu(Context context, View anchor) {
        super(context, anchor);
    }

    public DocumentToolBarPopu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
    }

    public DocumentToolBarPopu(Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
    }
}
