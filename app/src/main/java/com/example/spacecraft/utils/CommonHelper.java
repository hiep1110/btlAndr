package com.example.spacecraft.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

public class CommonHelper {
    public static Point getSizeDevice(Context context){
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            point.x = context.getSystemService(WindowManager.class).getCurrentWindowMetrics().getBounds().width();
            point.y = context.getSystemService(WindowManager.class).getCurrentWindowMetrics().getBounds().height();
        } else {
            context.getSystemService(WindowManager.class).getDefaultDisplay().getSize(point);
        }
        return point;
    }
}
