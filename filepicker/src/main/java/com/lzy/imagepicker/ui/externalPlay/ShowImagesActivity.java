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
    }

    private void initView() {
        tInfo = (TextView) findViewById(R.id.t_info);
    }
}
