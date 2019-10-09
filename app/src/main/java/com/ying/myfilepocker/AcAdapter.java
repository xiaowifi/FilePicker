package com.ying.myfilepocker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.imagepicker.ui.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class AcAdapter extends RecyclerView.Adapter<AcAdapter.StringHolder> {
    Context context;
    private final LayoutInflater inflater;

    List<ActivityBean> beans=new ArrayList<>();

    public void addItem(ActivityBean bean){
        beans.add(bean);
        notifyDataSetChanged();
    }

    public AcAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StringHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StringHolder(inflater.inflate(R.layout.item_string,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StringHolder holder, final int i) {
        holder.textView.setText(beans.get(i).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beans.get(i).a== VideoPlayerActivity.class){
                    VideoPlayerActivity.openActivity(context,"http://192.168.1.51:10010/renqin/uploadfile/file/7-9Vue%E9%A1%B9%E7%9B%AE%E9%A6%96%E9%A1%B5-%E9%A6%96%E9%A1%B5%E7%88%B6%E5%AD%90%E7%BB%84%E7%BB%84%E4%BB%B6%E9%97%B4%E4%BC%A0%E5%80%BC.mp4");
                }else {
                    context.startActivity(new Intent(context, beans.get(i).a));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class StringHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public StringHolder(@NonNull View itemView) {
            super(itemView);
            textView= (TextView) itemView;
        }
    }
}
