package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.lzy.imagepicker.MyFilePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.callback.PDFTouchCallBack;
import com.lzy.imagepicker.pdfviewer.PDFView;
import com.lzy.imagepicker.pdfviewer.util.FitPolicy;
import com.lzy.imagepicker.ui.fragement.PDFShowFragment;
import com.lzy.imagepicker.view.RedSpotView;

import java.io.File;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 全屏 状态 下 放弃 头条适配方案。
 */
public class PDFShowActivity extends AppCompatActivity implements CancelAdapt {
    String TAG="PDFShowActivity";
    private String path;


    public static void openactivity(Context context,String path){
       Intent intent= new Intent(context,PDFShowActivity.class);
       intent.putExtra("path",path);
       context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_pdfshow);
        path = getIntent().getStringExtra("path");
        Log.e(TAG, "onCreate: "+ path);
        openFragment(PDFShowFragment.class.getName());
    }
    public void openFragment(String className) {
        PDFShowFragment fragment= null;
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //隐藏所有的那个啥。
            for (Fragment fra : fragmentManager.getFragments()) {
                fragmentTransaction.hide(fra);
            }
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(className);
            if (fragmentByTag!=null){
                fragmentTransaction.show(fragmentByTag);
            }else {
                fragment = (PDFShowFragment) Class.forName(className).newInstance();
                fragment.setPath(path);
                fragmentTransaction.add(R.id.frame_file, fragment, className);
            }
            //添加到返回栈中
            // fragmentTransaction.addToBackStack(className);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
