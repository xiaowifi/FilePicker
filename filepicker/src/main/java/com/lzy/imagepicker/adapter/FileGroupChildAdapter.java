package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.FileItem;
import com.lzy.imagepicker.callback.OnFIlePickerChangeCallBack;
import com.lzy.imagepicker.util.IconUtils;

import java.util.ArrayList;
import java.util.List;

public class FileGroupChildAdapter extends RecyclerView.Adapter<FileGroupChildAdapter.ChildHolder> {

    Context context;
    private final LayoutInflater inflater;
    OnFIlePickerChangeCallBack changeCallBack;

    public void setChangeCallBack(OnFIlePickerChangeCallBack changeCallBack) {
        this.changeCallBack = changeCallBack;
    }

    List<FileItem> fileItems=new ArrayList<>();
    ArrayList<String> pickers=new ArrayList<>();
    public FileGroupChildAdapter(Context context, List<FileItem> fileItems) {
        this.context = context;
        this.fileItems = fileItems;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChildHolder(inflater.inflate(R.layout.item_group_file,viewGroup,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ChildHolder holder, final int i) {
        String path= fileItems.get(i).path;
        holder.img_type.setImageResource(pickers.contains(path)?R.drawable.ic_pick_t:R.drawable.ic_picker_f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickers.contains(fileItems.get(i).path)){
                    pickers.remove(fileItems.get(i).path);
                }else {
                    pickers.add(fileItems.get(i).path);
                }
                if (changeCallBack!=null){
                    changeCallBack.onCallback();
                }
                notifyItemChanged(i);

            }
        });
        holder.t_name.setText(fileItems.get(i).name);
        holder.t_info.setText(fileItems.get(i).addTime+"  "+fileItems.get(i).size);
        holder.image.setImageBitmap(IconUtils.getIconForPath(fileItems.get(i).path));
    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    class ChildHolder extends RecyclerView.ViewHolder{
        ImageView img_type;
        ImageView image;
        TextView t_name;
        TextView t_info;
        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            img_type=itemView.findViewById(R.id.img_type);
            image=itemView.findViewById(R.id.image);
            t_name=itemView.findViewById(R.id.t_name);
            t_info=itemView.findViewById(R.id.t_info);
        }
    }

}
