package com.lzy.imagepicker;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.lzy.imagepicker.okgo.OkGo;
import com.lzy.imagepicker.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.imagepicker.okserver.OkDownload;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyFilePicker {
    String TAG=this.getClass().getName();
    static MyFilePicker picker;
    boolean x5down=false;
    public static final String downPath= Environment.getExternalStorageDirectory()+"/yibaitong.down.file";

    public boolean isX5down() {
        return x5down;
    }

    private MyFilePicker() {
    }
    public static MyFilePicker init(){
        if (picker==null){
            picker=new MyFilePicker();
        }
        return picker;
    }

    /**
     * 初始化 X5
     * @param application
     */
    public void initX5(Application application){
        QbSdk.setDownloadWithoutWifi(true);
        //优化方案
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.e(TAG, "onCoreInitFinished: " );
            }

            @Override
            public void onViewInitFinished(boolean b) {
                x5down=b;
            }

        });
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("okhttp");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(application).setOkHttpClient(builder.build());
        OkDownload.getInstance().setFolder(downPath).getThreadPool().setCorePoolSize(1);
    }

    public int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;

    public void setFlag(int flag){
        this.flag=flag;

    }
}
