package com.loosu.soplayer.utils;

import android.annotation.SuppressLint;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;

import java.lang.reflect.Field;

public class PopupMenuUtil {
    @SuppressLint("RestrictedApi")
    public static void forceShowIcon(PopupMenu popupMenu, boolean showIcon) {
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
            mHelper.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
