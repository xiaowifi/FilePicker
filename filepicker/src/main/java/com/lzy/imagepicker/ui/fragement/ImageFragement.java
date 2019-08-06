package com.lzy.imagepicker.ui.fragement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.adapter.ImageGroupAdapter;
import com.lzy.imagepicker.bean.ImageFolder;
import com.lzy.imagepicker.bean.VideoFolder;
import com.lzy.imagepicker.source.ImageDataSource;
import com.lzy.imagepicker.source.VideoDataSource;

import java.util.List;

/**
 * 视频选择的碎片。
 */
public class ImageFragement extends Fragment implements ImageDataSource.OnImagesLoadedListener {

    private RecyclerView recy_child;
    private ImageGroupAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_recyle, container, false);
        recy_child = view.findViewById(R.id.recy_child);
        recy_child.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ImageGroupAdapter(getContext());
        recy_child.setAdapter(adapter);
        new ImageDataSource(this,null,this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public List<String> getPickers(){
        return adapter.getPickers();
    }


    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        if (imageFolders.size()>1){
            imageFolders.remove(0);
        }
        adapter.setFolders(imageFolders);
    }
}
