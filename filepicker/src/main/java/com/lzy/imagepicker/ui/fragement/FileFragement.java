package com.lzy.imagepicker.ui.fragement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.FileGroupAdapter;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.source.AudioDataSource;
import com.lzy.imagepicker.source.FileDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频选择的碎片。
 */
public class FileFragement extends Fragment implements FileDataSource.OnFileLoadedListener {
    String TAG=getClass().getName();
    private RecyclerView recy_child;
    ArrayList<String> querySuffixs=new ArrayList<>();
    private FileGroupAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_recyle, container, false);
        recy_child = view.findViewById(R.id.recy_child);
        recy_child.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FileGroupAdapter(getContext());
        recy_child.setAdapter(adapter);
        view.findViewById(R.id.lin_loading).setVisibility(View.VISIBLE);
        initQuerys();
        new FileDataSource(querySuffixs,this,null,this);
        return view;
    }

    private void initQuerys() {
        querySuffixs.clear();
        querySuffixs.add(".doc");
        querySuffixs.add(".docm");
        querySuffixs.add(".docx");
        querySuffixs.add(".dot");
        querySuffixs.add(".dotm");
        querySuffixs.add(".dotx");
        querySuffixs.add(".html");
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
    }

    public List<String> getPickers(){
        return adapter.getPickers();
    }


    @Override
    public void onFileLoaded(List<FileFolder> fileFolders) {
        getView().findViewById(R.id.lin_loading).setVisibility(View.GONE);
        Log.e(TAG, "onFileLoaded: "+new Gson().toJson(fileFolders));
        if (fileFolders.size()>1){
            fileFolders.remove(0);
        }
        adapter.setFileFolders(fileFolders);
    }
}
