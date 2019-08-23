package com.lzy.imagepicker.callback;

import android.view.MotionEvent;

public interface PDFTouchCallBack {
    //长按的时候。
     void onLongPress(MotionEvent event);
    //手指离开屏幕的时候。
     void onActionUp();
     void onMove(MotionEvent event);
}
