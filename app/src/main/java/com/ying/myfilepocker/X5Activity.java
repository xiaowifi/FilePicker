package com.ying.myfilepocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzy.imagepicker.view.X5WebView;

public class X5Activity extends AppCompatActivity {

    private X5WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_x5);
        webView = findViewById(R.id.web_x5);
        webView.loadUrl("http://debugtbs.qq.com/");
    }
}
