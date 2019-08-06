package com.lzy.imagepicker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.ui.fragement.AudioFragement;
import com.lzy.imagepicker.ui.fragement.FileFragement;
import com.lzy.imagepicker.ui.fragement.ImageFragement;
import com.lzy.imagepicker.ui.fragement.NoPermissionsFragment;
import com.lzy.imagepicker.ui.fragement.OtherFragement;
import com.lzy.imagepicker.ui.fragement.VideoFragement;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 文件 选择。
 */
public class FilePickerActivity extends BasePickerActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, ValueCallback<String> {

    private TextView t_select_info;
    private TextView t_commit;
    private RadioGroup radio_group_title;
    private LinearLayout lin_bottom;

    /**
     * 打开当前界面，
     *
     * @param activity
     * @param requestKey
     * @param requestCode
     * @param resultCode
     */
    public static void openActivity(Activity activity,  String requestKey, int requestCode, int resultCode) {
        Intent intent = new Intent(activity, FilePickerActivity.class);
        intent.putExtra("requestKey",requestKey);
        intent.putExtra("resultCode",resultCode);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_file_picker);
        //返回事件处理。
        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.t_back).setOnClickListener(this);
        findViewById(R.id.t_play).setOnClickListener(this);//预览。
        lin_bottom = findViewById(R.id.lin_bottom);
        t_select_info = findViewById(R.id.t_select_info);
        t_commit = findViewById(R.id.t_commit);
        t_commit.setOnClickListener(this);
        radio_group_title = findViewById(R.id.radio_group_title);
        radio_group_title.setOnCheckedChangeListener(this);
    }

    @Override
    public void getInfo() {
        lin_bottom.setVisibility(View.VISIBLE);
        radio_group_title.setVisibility(View.VISIBLE);
        radio_group_title.check(R.id.radio_image);
        //openFragment(ImageFragement.class.getName());
    }

    @Override
    public void onNoPermissionsResult() {
        //没有获取到权限弹窗。
        lin_bottom.setVisibility(View.GONE);
        radio_group_title.setVisibility(View.GONE);
        openFragment(NoPermissionsFragment.class.getName());
    }


    String nowClass;
    /**
     * 打开一个fragment
     *
     */
    public void openFragment(String className) {
        nowClass=className;
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.img_back){
            onBackPressed();
        }else if (id==R.id.t_back){
            onBackPressed();
        }else if (id==R.id.t_play){
            //预览
            List<String> pickers = getPickers(nowClass);
//            PreviewActivity.openActivity(view.getContext(),);
            openFileReader(view.getContext(),pickers.get(0));
        }else if (id==R.id.t_commit){
            //提交
        }
    }


    public void openFileReader(Context context, String pathName)
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("local", "true");
        JSONObject Object = new JSONObject();
        try
        {
            Object.put("pkgName",context.getApplicationContext().getPackageName());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        params.put("menuData",Object.toString());
        QbSdk.getMiniQBVersion(context);
        int ret = QbSdk.openFileReader(context, pathName, params, this);

    }


    private List<String> getPickers(String nowClass) {
        List<String> pickers=new ArrayList<>();
        if (nowClass!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(nowClass);
            if (fragmentByTag!=null){
                if (nowClass.equals(ImageFragement.class.getName())){

                    ImageFragement fragement= (ImageFragement) fragmentByTag;
                    pickers.addAll(fragement.getPickers());
                }else if (nowClass.equals(FileFragement.class.getName())){

                    FileFragement fragement= (FileFragement) fragmentByTag;
                    pickers.addAll(fragement.getPickers());
                }else if (nowClass.equals(VideoFragement.class.getName())){

                    VideoFragement fragement= (VideoFragement) fragmentByTag;
                    pickers.addAll(fragement.getPickers());
                }else if (nowClass.equals(AudioFragement.class.getName())){

                    AudioFragement fragement= (AudioFragement) fragmentByTag;
                    pickers.addAll(fragement.getPickers());
                }else if (nowClass.equals(OtherFragement.class.getName())){

                    OtherFragement fragement= (OtherFragement) fragmentByTag;
                    pickers.addAll(fragement.getPickers());
                }
            }
        }
        return pickers;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i==R.id.radio_image){
            //打开图片的fragment
            openFragment(ImageFragement.class.getName());
        }else if (i==R.id.radio_doc){
            openFragment(FileFragement.class.getName());
            //打开文档的fragment
        }else if (i==R.id.radio_video){
            openFragment(VideoFragement.class.getName());
            //视频的
        }else if (i==R.id.radio_audio){
            openFragment(AudioFragement.class.getName());
            //音频的
        }else if (i==R.id.radio_other){
            openFragment(OtherFragement.class.getName());
            //其他的类型。
        }
    }

    @Override
    public void onReceiveValue(String s) {
        Log.e(TAG, "onReceiveValue: "+s );
    }
}
