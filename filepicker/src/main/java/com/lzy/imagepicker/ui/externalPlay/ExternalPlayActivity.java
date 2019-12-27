package com.lzy.imagepicker.ui.externalPlay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.imagepicker.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 提供给外部使用的用于接收 其他应用 提供的界面样式。
 */
public class ExternalPlayActivity extends AppCompatActivity {

    private TextView tInfo;

    String [] minitypes={"application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
            "text/rtf",
            "text/xml",
            "application/pdf",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.template",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            "application/x-freemind",
    };
    private ArrayList<String> miniTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_external_play);
        initView();
        getIntentDatas();
    }

    private void getIntentDatas() {
        miniTypes = new ArrayList<>();
        miniTypes.addAll( Arrays.asList(minitypes));
        StringBuffer buffer = new StringBuffer();
        buffer.append("初始化：");
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            buffer.append("单个文件：").append(uri.getPath());
            buffer.append(":").append(type);
            if (type.contains("image")){
                //表示这个是图片。
            }else if (type.contains("audio")){
                //音频
            }else if (type.contains("video")){
                //视频
            }else {
                //其他文件。

            }

        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            ArrayList<Uri> arrayList = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            buffer.append("多个文件：").append(arrayList.toString());
            buffer.append(":").append(type);
            if (type.contains("image")){
                //表示这个是图片。
            }else if (type.contains("audio")){
                //音频
            }else if (type.contains("video")){
                //视频
            }else {
                //其他文件。
            }
        }
        tInfo.setText(buffer);

    }

    private void initView() {
        tInfo = (TextView) findViewById(R.id.t_info);
    }
}
