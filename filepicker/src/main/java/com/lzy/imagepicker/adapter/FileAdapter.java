package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.FileItem;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter <FileAdapter.FileHolder>{

    Context context;
    private final LayoutInflater inflater;
    List<FileItem> fileItems=new ArrayList<>();

    public FileAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setFileItems(List<FileItem> fileItems) {
        this.fileItems = fileItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FileHolder(inflater.inflate(R.layout.item_file,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int i) {
        holder.t_1.setText(fileItems.get(i).name);
        holder.t_2.setText(fileItems.get(i).path);
        holder.t_3.setText(fileItems.get(i).size+"");
        holder.t_4.setText(fileItems.get(i).mimeType+"");
        holder.t_5.setText(fileItems.get(i).addTime+"");
        holder.t_6.setText(fileItems.get(i).title);
    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    class FileHolder extends RecyclerView.ViewHolder{
        TextView t_1;
        TextView t_2;
        TextView t_3;
        TextView t_4;
        TextView t_5;
        TextView t_6;
        public FileHolder(@NonNull View itemView) {
            super(itemView);
            t_1=itemView.findViewById(R.id.t_1);
            t_2=itemView.findViewById(R.id.t_2);
            t_3=itemView.findViewById(R.id.t_3);
            t_4=itemView.findViewById(R.id.t_4);
            t_5=itemView.findViewById(R.id.t_5);
            t_6=itemView.findViewById(R.id.t_6);
        }
    }
}
