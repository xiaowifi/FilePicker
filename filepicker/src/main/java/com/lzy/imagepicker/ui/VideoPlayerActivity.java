package com.lzy.imagepicker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.video.LyVideoView;

public class VideoPlayerActivity extends AppCompatActivity {
    String path = "http://192.168.1.51:10010/renqin/uploadfile/file/7-9Vue%E9%A1%B9%E7%9B%AE%E9%A6%96%E9%A1%B5-%E9%A6%96%E9%A1%B5%E7%88%B6%E5%AD%90%E7%BB%84%E7%BB%84%E4%BB%B6%E9%97%B4%E4%BC%A0%E5%80%BC.mp4";
    private LyVideoView lyVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_video_player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        lyVideo = (LyVideoView) findViewById(R.id.ly_video);
        lyVideo.setPath(path);
        lyVideo.start();
        lyVideo.getImg_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //todo  在这里 直接 销毁。
        lyVideo.stopPlayback();
        super.onBackPressed();
    }
}
