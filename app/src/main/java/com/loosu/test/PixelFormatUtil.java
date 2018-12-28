package com.loosu.test;

import android.graphics.PixelFormat;

public class PixelFormatUtil {
    public static String formatToString(int format) {
        switch (format) {
            case PixelFormat.UNKNOWN:
                return "UNKNOWN";
            case PixelFormat.TRANSLUCENT:
                return "TRANSLUCENT";
            case PixelFormat.TRANSPARENT:
                return "TRANSPARENT";
            case PixelFormat.RGBA_8888:
                return "RGBA_8888";
            case PixelFormat.RGBX_8888:
                return "RGBX_8888";
            case PixelFormat.RGB_888:
                return "RGB_888";
            case PixelFormat.RGB_565:
                return "RGB_565";
            case PixelFormat.RGBA_5551:
                return "RGBA_5551";
            case PixelFormat.RGBA_4444:
                return "RGBA_4444";
            case PixelFormat.A_8:
                return "A_8";
            case PixelFormat.L_8:
                return "L_8";
            case PixelFormat.LA_88:
                return "LA_88";
            case PixelFormat.RGB_332:
                return "RGB_332";
            case PixelFormat.YCbCr_422_SP:
                return "YCbCr_422_SP";
            case PixelFormat.YCbCr_420_SP:
                return "YCbCr_420_SP";
            case PixelFormat.YCbCr_422_I:
                return "YCbCr_422_I";
            case PixelFormat.RGBA_F16:
                return "RGBA_F16";
            case PixelFormat.RGBA_1010102:
                return "RGBA_1010102";
            case PixelFormat.JPEG:
                return "JPEG";
            default:
                return Integer.toString(format);
        }
    }
}
