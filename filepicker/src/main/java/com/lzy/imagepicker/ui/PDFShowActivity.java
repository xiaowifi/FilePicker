package com.lzy.imagepicker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.lzy.imagepicker.R;

import java.io.File;

public class PDFShowActivity extends AppCompatActivity {

    private PDFView pdf_view;
    private LinearLayout lin_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_pdfshow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String path= getIntent().getStringExtra("path");
        pdf_view = findViewById(R.id.pdf_view);
        pdf_view.fromFile(new File(path))
                .fitEachPage(true)//页面相对于界面。
                .enableAnnotationRendering(true)//显示注释
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
        lin_content = findViewById(R.id.lin_content);
        findViewById(R.id.t_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf_view.jumpTo(pdf_view.getCurrentPage()-1);
            }
        });
        findViewById(R.id.t_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf_view.jumpTo(pdf_view.getCurrentPage()+1);
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}
