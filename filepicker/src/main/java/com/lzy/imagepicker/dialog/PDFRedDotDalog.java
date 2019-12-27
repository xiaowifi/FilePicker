package com.lzy.imagepicker.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.RedDotSetAdapter;
import com.lzy.imagepicker.view.RedDotView;

public class PDFRedDotDalog extends DialogFragment {
    String TAG="PDFRedDotDalog";
    private SeekBar seekbar;
    private RecyclerView recy_center;
    private RecyclerView recy_c;
    private RedDotSetAdapter centerAdapter;
    private RedDotSetAdapter cadapter;
    private RedDotView dot_view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View view= inflater.inflate(R.layout.dialog_pdf_red_dot,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        seekbar = view.findViewById(R.id.seekbar);
        recy_center = view.findViewById(R.id.recy_center);
        recy_c = view.findViewById(R.id.recy_c);
        dot_view = view.findViewById(R.id.dot_view);
        recy_center.setLayoutManager(new GridLayoutManager(getContext(),8));
        recy_c.setLayoutManager(new GridLayoutManager(getContext(),8));
        centerAdapter = new RedDotSetAdapter(getContext());
        centerAdapter.setOnItemCallBack(new RedDotSetAdapter.OnItemCallBack() {
            @Override
            public void onCallBack(int color) {
                dot_view.onShow(color,cadapter.getSelectColor(),seekbar.getProgress());
            }
        });
        recy_center.setAdapter(centerAdapter);
        cadapter = new RedDotSetAdapter(getContext());
        cadapter.setOnItemCallBack(new RedDotSetAdapter.OnItemCallBack() {
            @Override
            public void onCallBack(int color) {
                dot_view.onShow(centerAdapter.getSelectColor(),color,seekbar.getProgress());
            }
        });
        cadapter.setSelect(0);
        recy_c.setAdapter(cadapter);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e(TAG, "onProgressChanged: "+b +"-------"+i);
                if (true){
                    dot_view.onShow(centerAdapter.getSelectColor(),cadapter.getSelectColor(),i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        view.findViewById(R.id.t_dissmiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack!=null){
                    callBack.onBack(centerAdapter.getSelectColor(),cadapter.getSelectColor(),seekbar.getProgress());
                }
                dismiss();
            }
        });
    }

    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
        void onBack(int center_c,int edge_c,int dpSpot);
    }
}
