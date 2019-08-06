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
import com.lzy.imagepicker.adapter.FileGroupAdapter;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.source.FileDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他文件类型。
 */
public class OtherFragement extends Fragment implements FileDataSource.OnFileLoadedListener {

    private RecyclerView recy_child;
    ArrayList<String> querySuffixs=new ArrayList<>();
    private FileGroupAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_recyle, container, false);
        recy_child = view.findViewById(R.id.recy_child);
        recy_child.setLayoutManager(new LinearLayoutManager(getContext()));
        querySuffixs.clear();
        querySuffixs.add(".json");
        querySuffixs.add(".svg");
        adapter = new FileGroupAdapter(getContext());
        recy_child.setAdapter(adapter);
        new FileDataSource(this,this);
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
    public void onFileLoaded(List<FileFolder> fileFolders) {
        if (fileFolders.size()>1){
            fileFolders.remove(0);
        }
        adapter.setFileFolders(fileFolders);
    }
}
