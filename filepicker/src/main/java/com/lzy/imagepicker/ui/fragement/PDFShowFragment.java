package com.lzy.imagepicker.ui.fragement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.callback.PDFTouchCallBack;
import com.lzy.imagepicker.dialog.PDFRedDotDalog;
import com.lzy.imagepicker.pdfviewer.PDFView;
import com.lzy.imagepicker.pdfviewer.util.FitPolicy;
import com.lzy.imagepicker.view.RedSpotView;

import java.io.File;

public class PDFShowFragment extends Fragment {
    String TAG="PDFShowFragment";
    String path;
    private PDFView pdfView;
    private ImageView imgBack;
    private RedSpotView redView;
    private ImageView imgSet;

    public PDFShowFragment setPath(String path) {
        this.path = path;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fr_pdf_show, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        pdfView = (PDFView)  view.findViewById(R.id.pdf_view);
        imgBack = (ImageView) view. findViewById(R.id.img_back);
        redView = (RedSpotView) view. findViewById(R.id.red_view);
        imgSet = view.findViewById(R.id.img_pdf_set);
        pdfView.setTouchCallBack(new PDFTouchCallBack() {
            @Override
            public void onLongPress(MotionEvent event) {
                redView.setVisibility(View.VISIBLE);
                redView.onShow(event.getX(),event.getY());
            }

            @Override
            public void onActionUp() {
                redView.setVisibility(View.GONE);
            }

            @Override
            public void onMove(MotionEvent event) {
                Log.e(TAG, "onMove: " );
                redView.onShow(event.getX(),event.getY());
            }
        });
        Log.e(TAG, "initView: "+path );
        pdfView.fromFile(new File(path))
                .fitEachPage(true)//页面相对于界面。
                .enableAnnotationRendering(true)//显示注释
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        imgSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetDialog();
            }
        });
    }

    private void showSetDialog() {
        PDFRedDotDalog redDalog=new PDFRedDotDalog();
        redDalog.setCallBack(new PDFRedDotDalog.CallBack() {
            @Override
            public void onBack(int center_c, int edge_c, int dpSpot) {
                redView.setConfigs(center_c,edge_c,dpSpot);
            }
        });
        redDalog.show(getChildFragmentManager(),"PDFRedDotDalog");
    }
}
