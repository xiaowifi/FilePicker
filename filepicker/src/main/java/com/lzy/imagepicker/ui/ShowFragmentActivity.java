package com.lzy.imagepicker.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.ui.fragement.DocumentPreviewFragment;

public class ShowFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_show_fragment);
        openFragment(DocumentPreviewFragment.class.getName());
    }
    public void openFragment(String className) {
        Fragment fragment= null;
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
                fragment = (Fragment) Class.forName(className).newInstance();
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
