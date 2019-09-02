package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.view.SuperFileView;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

public class YulanActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {

    String path;
    private RelativeLayout re_contentƒ;
    private TbsReaderView mTbsReaderView;

    public static void openActivity(Context context,String path){
        Intent intent= new Intent(context,YulanActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_yulan);
        path= getIntent().getStringExtra("path");
        re_contentƒ = findViewById(R.id.re_content);
        mTbsReaderView = new TbsReaderView(this, this);
        re_contentƒ.addView(mTbsReaderView,new RelativeLayout.LayoutParams(-1,-1));
        displayFile(path);


    }
    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";

    /**
     *
     * @param filePath
     */
    private void displayFile(String filePath) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile =new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.e("YulanActivity","准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
                Log.e("YulanActivity","创建/TbsReaderTemp失败！！！！！");
            }
        }
        Log.e("YulanActivity", "displayFile: "+path);
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        String [] paths= path.split("\\.");
        if (paths.length==2){
            boolean result = mTbsReaderView.preOpen(paths[1], false);
            Log.e("YulanActivity","查看文档---"+result);
            if (result) {
                mTbsReaderView.openFile(bundle);
            }else{
                Log.e("YulanActivity", "displayFile: " );
            }
        }

    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e("YulanActivity", "onCallBackAction: "+integer );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
