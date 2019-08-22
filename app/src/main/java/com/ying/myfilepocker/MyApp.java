package com.ying.myfilepocker;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

public class MyApp extends Application {
    String TAG="YulanActivity";
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.e(TAG, "onCoreInitFinished: " );
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e(TAG, "onViewInitFinished: "+b );

            }

        });
        QbSdk.setTbsListener(new TbsListener(){
            @Override
            public void onDownloadFinish(int i) {
                Log.e(TAG, "onDownloadFinish: "+i );
            }

            @Override
            public void onInstallFinish(int i) {
                Log.e(TAG, "onInstallFinish: " +i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.e(TAG, "onDownloadProgress: "+i );
            }
        });
    }
}
