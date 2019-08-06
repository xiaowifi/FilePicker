package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.R;

public class GroupAdapter extends RecyclerView.Adapter <GroupAdapter.GroupHolder>{
    Context context;
    private final LayoutInflater inflater;

    public GroupAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GroupHolder(inflater.inflate(R.layout.item_group_recy,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder groupHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
