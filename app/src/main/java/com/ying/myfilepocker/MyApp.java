package com.ying.myfilepocker;

import android.support.multidex.MultiDexApplication;

import com.lzy.imagepicker.MyFilePicker;

public class MyApp extends MultiDexApplication {
    String TAG="YulanActivity";
    @Override
    public void onCreate() {
        super.onCreate();
        MyFilePicker.init().initX5(this);
    }
}
