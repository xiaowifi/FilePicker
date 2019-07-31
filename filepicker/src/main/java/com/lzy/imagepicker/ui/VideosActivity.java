package com.lzy.imagepicker.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.VideoAdapter;
import com.lzy.imagepicker.bean.VideoFolder;
import com.lzy.imagepicker.source.VideoDataSource;

import java.util.List;

/**
 * 视频列表。
 */
public class VideosActivity extends BasePickerActivity implements VideoDataSource.OnVideoLoadedListener {

    private RecyclerView recy_child;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_video);
        recy_child = findViewById(R.id.recy_child);
        recy_child.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this);
        recy_child.setAdapter(adapter);
    }

    @Override
    public void getInfo() {
        Log.e(TAG, "getInfo: " );
        new VideoDataSource(this,this);
    }

    @Override
    public void onVideoLoaded(List<VideoFolder> videoFolders) {
        adapter.setVideoItems(videoFolders.get(0).videos);
        Log.e(TAG, "onVideoLoaded: "+new Gson().toJson(videoFolders));
    }
}
