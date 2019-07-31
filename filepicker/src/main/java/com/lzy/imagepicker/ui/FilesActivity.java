package com.lzy.imagepicker.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.FileAdapter;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.source.FileDataSource;

import java.util.ArrayList;
import java.util.List;

public class FilesActivity extends BasePickerActivity implements FileDataSource.OnFileLoadedListener {

    private RecyclerView recy_child;
    private FileAdapter adapter;

    ArrayList<String> querys=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_files);
        recy_child = findViewById(R.id.recy_child);
        recy_child.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new FileAdapter(this);
        recy_child.setAdapter(adapter);
    }

    @Override
    public void getInfo() {
        querys.clear();
        querys.add(".txt");
        querys.add(".ex");
        new FileDataSource(querys,this,this,null);
    }

    @Override
    public void onFileLoaded(List<FileFolder> fileFolders) {
        Log.e(TAG, "onFileLoaded: "+ new Gson().toJson(fileFolders));
        adapter.setFileItems(fileFolders.get(0).fileItems);
    }
}
