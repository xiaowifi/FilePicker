package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.server.LocalServer;
import com.tencent.smtt.sdk.WebView;

import java.io.UnsupportedEncodingException;

public class PreviewActivity extends AppCompatActivity {

    String TAG="PreviewActivity";

    LocalServer server=new LocalServer();
    private WebView web_view;

    public static void openActivity(Context context,String path){
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        server.execute();//启动
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_preview);
        web_view = findViewById(R.id.web_view);
        String path = getIntent().getStringExtra("path");

        String httpurl= null;
        try {
            httpurl = server.createLocalHttpUrl(path);
            Log.e(TAG, "onCreate: "+path );
            Log.e(TAG, "onCreate: "+httpurl );
           web_view.loadUrl(httpurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: "+e );
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        server.finish();
    }
}
