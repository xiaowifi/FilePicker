package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.ImageFolder;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupAdapter extends RecyclerView.Adapter <ImageGroupAdapter.GroupHolder>{
    Context context;
    private final LayoutInflater inflater;
    List<ImageFolder> folders=new ArrayList<>();
    ArrayMap<Integer,ImageAdapter> chidAdapters=new ArrayMap<>();
    public void setFolders(List<ImageFolder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    public ImageGroupAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<String> getPickers(){
        List<String> pickers=new ArrayList<>();
        for (ImageAdapter adapter: chidAdapters.values()){
            pickers.addAll(adapter.getPickers());
        }
        return pickers;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GroupHolder(inflater.inflate(R.layout.item_group_recy,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupHolder holder, int i) {
        holder.t_title.setText(folders.get(i).name);
        holder.recy_child.setVisibility(i==0?View.VISIBLE:View.GONE);
        holder.img_type.setImageResource(holder.recy_child.getVisibility()==View.VISIBLE?R.drawable.ic_array_top:R.drawable.ic_array_bottom);
        holder.recy_child.setLayoutManager(new GridLayoutManager(context,5));
        holder.lin_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.recy_child.setVisibility(holder.recy_child.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
            }
        });
        if (chidAdapters.containsKey(i)){
            holder.recy_child.setAdapter(chidAdapters.get(i));
        }else {
            ImageAdapter adapter=new ImageAdapter(context);
            adapter.setImageItems(folders.get(i).images);
            chidAdapters.put(i,adapter);
            holder.recy_child.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class GroupHolder extends RecyclerView.ViewHolder{
        ImageView img_type;
        TextView t_title;
        RecyclerView recy_child;
        LinearLayout lin_title;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type);
            t_title=itemView.findViewById(R.id.t_title);
            recy_child=itemView.findViewById(R.id.recy_child);
            lin_title=itemView.findViewById(R.id.lin_title);
        }
    }
}
