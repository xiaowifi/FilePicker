package com.ying.myfilepocker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.FilePickerActivity;
import com.lzy.imagepicker.ui.FilesActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.VideosActivity;
import com.lzy.imagepicker.util.IconUtils;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.smtt.sdk.QbSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recy_chid;
    private AcAdapter acAdapter;
    private ImageView img_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recy_chid = findViewById(R.id.recy_chid);
        recy_chid.setLayoutManager(new LinearLayoutManager(this));
        acAdapter = new AcAdapter(this);
        recy_chid.setAdapter(acAdapter);
        acAdapter.addItem(new ActivityBean(VideosActivity.class,"查询视频列表"));
        acAdapter.addItem(new ActivityBean(FilesActivity.class,"文件查询"));
        acAdapter.addItem(new ActivityBean(FilePickerActivity.class,"仿QQ文件列表"));
        initPicler();
        findViewById(R.id.t_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(), ImageGridActivity.class);
                startActivityForResult(intent1, 5);
            }
        });
        img_icon = findViewById(R.id.img_icon);
        img_icon.setImageBitmap(IconUtils.getIconForPath("qqqq.doc"));

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
       // openFileReader(this,);
    }

    private void initPicler() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(6);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
}
