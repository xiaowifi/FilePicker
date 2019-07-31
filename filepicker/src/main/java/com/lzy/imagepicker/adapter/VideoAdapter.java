package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private final LayoutInflater inflater;
    List<VideoItem> videoItems=new ArrayList<>();
    public VideoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoHolder(inflater.inflate(R.layout.item_file,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VideoHolder holder= (VideoHolder) viewHolder;
        holder.t_1.setText(videoItems.get(i).name);
        holder.t_2.setText(videoItems.get(i).path);
        holder.t_3.setText(videoItems.get(i).size+" ");
        holder.t_4.setText(videoItems.get(i).width+"");
        holder.t_5.setText(videoItems.get(i).height+" ");
        holder.t_5.setText(videoItems.get(i).mimeType+"");
        holder.t_6.setText(videoItems.get(i).addTime+"");
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder{
        TextView t_1;
        TextView t_2;
        TextView t_3;
        TextView t_4;
        TextView t_5;
        TextView t_6;
        public VideoHolder(@NonNull View itemView) {
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
