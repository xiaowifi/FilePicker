package com.lzy.imagepicker.ui.externalPlay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.imagepicker.R;

import java.util.ArrayList;

/**
 * 允许获取外部 传入的问题。
 *
 * @author yangfan
 */
public class ShowImagesActivity extends AppCompatActivity {

    private TextView tInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_show_images);
        initView();
        StringBuffer buffer = new StringBuffer();
        buffer.append("初始化：");
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            buffer.append("单个文件：" + uri.getPath());
            if ("audio/".equals(type)) {
                // 处理发送来音频

            } else if (type.startsWith("video/")) {
                // 处理发送来的视频
            } else if (type.startsWith("*/")) {
                //处理发送过来的其他文件
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            ArrayList<Uri> arrayList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            buffer.append("多个文件：" + arrayList.toString());
            if (type.startsWith("audio/")) {
                // 处理发送来的多个音频
            } else if (type.startsWith("video/")) {
                //处理发送过来的多个视频
            } else if (type.startsWith("*/")) {
                //处理发送过来的多个文件
            }
        }
        tInfo.setText(buffer);
    }

    private void initView() {
        tInfo = (TextView) findViewById(R.id.t_info);
    }
}
