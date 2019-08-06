package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.util.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    Context context;
    private final LayoutInflater inflater;
    List<ImageItem> imageItems=new ArrayList<>();
    ArrayList<String> pickers=new ArrayList<>();

    public ArrayList<String> getPickers() {
        return pickers;
    }

    public ImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setImageItems(List<ImageItem> imageItems) {
        this.imageItems = imageItems;
        pickers.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageHolder(inflater.inflate(R.layout.item_group_image,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, final int i) {
        String path= imageItems.get(i).path;
        holder.img_type.setImageResource(pickers.contains(path)?R.drawable.ic_pick_t:R.drawable.ic_picker_f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickers.contains(imageItems.get(i).path)){
                    pickers.remove(imageItems.get(i).path);
                }else {
                    pickers.add(imageItems.get(i).path);
                }
                notifyItemChanged(i);
            }
        });
        //加载本地图片。
        GlideImageLoader.LoadLocalImage(path,holder.image);
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        ImageView image;
        ImageView img_type;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            img_type= itemView.findViewById(R.id.img_type);
        }
    }
}
