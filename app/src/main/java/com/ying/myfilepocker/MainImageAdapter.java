package com.ying.myfilepocker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.util.GlideImageLoader;
import com.lzy.imagepicker.util.StartBitmapUtils;

public class MainImageAdapter extends RecyclerView.Adapter<MainImageAdapter.ImageHolder> {

    Context context;

    String path="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567764472396&di=b1d356f21db9ab9eb3679e861ad999cd&imgtype=0&src=http%3A%2F%2Fimg.99danji.com%2Fuploadfile%2F2017%2F1122%2F20171122051423532.jpg";

    public MainImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_main_image,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int i) {
        holder.image.setImageBitmap(new StartBitmapUtils(context,200,i+1).getBitmap());
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ImageHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
