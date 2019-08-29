package com.lzy.imagepicker;

import android.app.Application;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

public class MyFilePicker {
    String TAG=this.getClass().getName();
    static MyFilePicker picker;
    boolean x5down=false;

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
        OkGo.getInstance().init(application);
    }

}
