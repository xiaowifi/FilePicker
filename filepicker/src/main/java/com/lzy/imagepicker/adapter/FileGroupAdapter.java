package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.FileFolder;
import com.lzy.imagepicker.callback.OnFIlePickerChangeCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件的列表。
 */
public class FileGroupAdapter extends RecyclerView.Adapter <FileGroupAdapter.GroupHolder>{
    Context context;
    private final LayoutInflater inflater;
    List<FileFolder> folders=new ArrayList<>();
    ArrayMap<Integer,FileGroupChildAdapter> chidAdapters=new ArrayMap<>();
    OnFIlePickerChangeCallBack onFIlePickerChangeCallBack;

    public void setOnFIlePickerChangeCallBack(OnFIlePickerChangeCallBack onFIlePickerChangeCallBack) {
        this.onFIlePickerChangeCallBack = onFIlePickerChangeCallBack;
    }

    public void setFileFolders(List<FileFolder> fileFolders) {
        this.folders = fileFolders;
        notifyDataSetChanged();
    }

    public FileGroupAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<String> getPickers(){
        List<String> p=new ArrayList<>();
        for (FileGroupChildAdapter adapter: chidAdapters.values()){
            p.addAll(adapter.pickers);
        }
        return p;
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
        holder.recy_child.setLayoutManager(new LinearLayoutManager(context));
        holder.lin_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.recy_child.setVisibility(holder.recy_child.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
            }
        });
        if (chidAdapters.containsKey(i)){
            holder.recy_child.setAdapter(chidAdapters.get(i));
        }else {
            FileGroupChildAdapter adapter=new FileGroupChildAdapter(context,folders.get(i).fileItems);
            chidAdapters.put(i,adapter);
            if (onFIlePickerChangeCallBack!=null){
                adapter.setChangeCallBack(onFIlePickerChangeCallBack);
            }

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
