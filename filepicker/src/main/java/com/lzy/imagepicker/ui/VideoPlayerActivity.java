package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.dialog.PDFRedDotDalog;
import com.lzy.imagepicker.video.LyVideoView;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * 视频播放。放弃头条适配方案。
 */
public class VideoPlayerActivity extends AppCompatActivity implements CancelAdapt {
    String path = "http://192.168.1.51:10010/renqin/uploadfile/file/7-9Vue%E9%A1%B9%E7%9B%AE%E9%A6%96%E9%A1%B5-%E9%A6%96%E9%A1%B5%E7%88%B6%E5%AD%90%E7%BB%84%E7%BB%84%E4%BB%B6%E9%97%B4%E4%BC%A0%E5%80%BC.mp4";
    private LyVideoView lyVideo;

    public static void openActivity(Context context,String path){
        Intent intent=new Intent(context,VideoPlayerActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_video_player);
        initView();
    }

    private void initView() {
        path= getIntent().getStringExtra("path");
        lyVideo = (LyVideoView) findViewById(R.id.ly_video);
        lyVideo.setPath(path);
        lyVideo.start();
        lyVideo.getImg_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lyVideo.getImg_set().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        PDFRedDotDalog redDalog=new PDFRedDotDalog();
        redDalog.setCallBack(new PDFRedDotDalog.CallBack() {
            @Override
            public void onBack(int center_c, int edge_c, int dpSpot) {
                lyVideo.getRed_view().setConfigs(center_c,edge_c,dpSpot);
            }
        });
        redDalog.show(getSupportFragmentManager(),"PDFRedDotDalog");
    }

    @Override
    public void onBackPressed() {
        //todo  在这里 直接 销毁。
        lyVideo.stopPlayback();
        super.onBackPressed();
    }
}
