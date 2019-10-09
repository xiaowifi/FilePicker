package com.lzy.imagepicker.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.FileGroupAdapter;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.callback.OnFIlePickerChangeCallBack;
import com.lzy.imagepicker.source.FileDataByMiniTypeSource;
import com.lzy.imagepicker.ui.BasePickerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件查找，通过minitype
 */
public class FilePickerByMiniTypeActivity extends BasePickerActivity implements View.OnClickListener, FileDataByMiniTypeSource.OnFileLoadedListener, OnFIlePickerChangeCallBack {

    private ImageView imgBack;
    private TextView tBack;
    private LinearLayout linBottom;
    private TextView tPlay;
    private TextView tSelectInfo;
    private TextView tCommit;
    ArrayList<String> querySuffixs = new ArrayList<>();
    private RecyclerView recyChild;
    private FileGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_file_picker_by_mini_type);
        initView();
        jurisdiction();
    }

    @Override
    public void getInfo() {
        initQuerys();
        FileDataByMiniTypeSource source = new FileDataByMiniTypeSource(querySuffixs, this, this, null);
    }

    /**
     * 获取将要查询的后缀名。
     */
    private void initQuerys() {
        querySuffixs.clear();
        querySuffixs.add(".doc");
        querySuffixs.add(".docm");
        querySuffixs.add(".docx");
        querySuffixs.add(".dot");
        querySuffixs.add(".dotm");
        querySuffixs.add(".dotx");
        querySuffixs.add(".mht");
        querySuffixs.add(".rtf");
        querySuffixs.add(".wps");
        querySuffixs.add(".wpt");
        querySuffixs.add(".xml");
        querySuffixs.add(".pdf");
        querySuffixs.add(".dbf");
        querySuffixs.add(".et");
        querySuffixs.add(".ett");
        querySuffixs.add(".xls");
        querySuffixs.add(".xlsm");
        querySuffixs.add(".xlsx");
        querySuffixs.add(".xlt");
        querySuffixs.add(".xltm");
        querySuffixs.add(".xltx");
        querySuffixs.add(".rtf");
        querySuffixs.add(".tip");
        querySuffixs.add(".dps");
        querySuffixs.add(".dpt");
        querySuffixs.add(".pot");
        querySuffixs.add(".potm");
        querySuffixs.add(".pps");
        querySuffixs.add(".ppt");
        querySuffixs.add(".pptm");
        querySuffixs.add(".pptx");
        querySuffixs.add(".emmx");
        querySuffixs.add(".mmap");
        querySuffixs.add(".mm");
        querySuffixs.add(".mp4");
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        tBack = (TextView) findViewById(R.id.t_back);
        linBottom = (LinearLayout) findViewById(R.id.lin_bottom);
        tPlay = (TextView) findViewById(R.id.t_play);
        tSelectInfo = (TextView) findViewById(R.id.t_select_info);
        tCommit = (TextView) findViewById(R.id.t_commit);

        imgBack.setOnClickListener(this);
        tBack.setOnClickListener(this);
        tPlay.setOnClickListener(this);
        tCommit.setOnClickListener(this);
        recyChild = (RecyclerView) findViewById(R.id.recy_child);
        recyChild.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileGroupAdapter(this);
        recyChild.setAdapter(adapter);
        adapter.setOnFIlePickerChangeCallBack(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        }else if (id==R.id.t_commit){
            //提交
        }else if (id==R.id.t_play){
            //预览。
            if (adapter.getPickers().size()==0){

                return;
            }
            DocumentsPreviewActivity.openactivity(this,adapter.getPickers());
        }
    }

    @Override
    public void onFileLoaded(List<FileFolder> fileFolders) {
        adapter.setFileFolders(fileFolders);
    }

    @Override
    public void onCallback() {
        tSelectInfo.setText("确定("+adapter.getPickers().size()+")");
    }
}
