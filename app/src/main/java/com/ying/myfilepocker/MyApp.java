package com.ying.myfilepocker;

import android.app.Application;

import com.lzy.imagepicker.MyFilePicker;

public class MyApp extends Application {
    String TAG="YulanActivity";
    @Override
    public void onCreate() {
        super.onCreate();
        MyFilePicker.init().initX5(this);
    }
}
