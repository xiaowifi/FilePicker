package com.lzy.imagepicker.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.lzy.imagepicker.R;
import com.lzy.imagepicker.callback.PDFTouchCallBack;
import com.lzy.imagepicker.pdfviewer.PDFView;
import com.lzy.imagepicker.pdfviewer.util.FitPolicy;
import com.lzy.imagepicker.view.RedSpotView;

import java.io.File;

public class PDFShowActivity extends AppCompatActivity {
    String TAG="PDFShowActivity";
    private PDFView pdf_view;
    private RedSpotView red_view;

    public static void openactivity(Context context,String path){
       Intent intent= new Intent(context,PDFShowActivity.class);
       intent.putExtra("path",path);
       context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_pdfshow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String path= getIntent().getStringExtra("path");
        pdf_view = findViewById(R.id.pdf_view);
        red_view = findViewById(R.id.red_view);
        pdf_view.setTouchCallBack(new PDFTouchCallBack() {
            @Override
            public void onLongPress(MotionEvent event) {
                red_view.setVisibility(View.VISIBLE);
                red_view.onShow(event.getX(),event.getY());
            }

            @Override
            public void onActionUp() {
                red_view.setVisibility(View.GONE);
            }

            @Override
            public void onMove(MotionEvent event) {
                Log.e(TAG, "onMove: " );
                red_view.onShow(event.getX(),event.getY());
            }
        });
        pdf_view.fromFile(new File(path))
                .fitEachPage(true)//页面相对于界面。
                .enableAnnotationRendering(true)//显示注释
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " );
        return super.onTouchEvent(event);
    }
}
