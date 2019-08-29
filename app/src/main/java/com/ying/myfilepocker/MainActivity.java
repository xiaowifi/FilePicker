package com.ying.myfilepocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.PDFPlayActivity;
import com.lzy.imagepicker.ui.FilePickerActivity;
import com.lzy.imagepicker.ui.FilePickerByMiniTypeActivity;
import com.lzy.imagepicker.ui.FilesActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ShowFragmentActivity;
import com.lzy.imagepicker.ui.VideosActivity;
import com.lzy.imagepicker.ui.X5Activity;
import com.lzy.imagepicker.util.IconUtils;
import com.lzy.imagepicker.view.CropImageView;

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
        acAdapter.addItem(new ActivityBean(X5Activity.class,"X5内核测试"));
        acAdapter.addItem(new ActivityBean(FilePickerByMiniTypeActivity.class,"mini type 文件查询"));
        acAdapter.addItem(new ActivityBean(RedSpotActivity.class,"小红点测试"));
        acAdapter.addItem(new ActivityBean(PDFPlayActivity.class,"pdf编辑"));
        acAdapter.addItem(new ActivityBean(ShowFragmentActivity.class,"fragment 预览加载"));
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
