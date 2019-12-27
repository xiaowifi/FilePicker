package com.lzy.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.imagepicker.R;

public class RedDotSetAdapter extends RecyclerView.Adapter<RedDotSetAdapter.RedDotHolder> {
    int [] colors={ R.color.read_c_1,
            R.color.read_c_2,
            R.color.read_c_3,
            R.color.read_c_4,
            R.color.read_c_5,
            R.color.read_c_6,
            R.color.read_c_7,
            R.color.read_c_8
    };

    Context context;
    private final LayoutInflater inflater;

    int select=2;

    public void setSelect(int select) {
        this.select = select;
    }

    public int getSelectColor(){
        return context.getResources().getColor(colors[select]);
    }

    public RedDotSetAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RedDotHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RedDotHolder(inflater.inflate(R.layout.item_red_set,viewGroup,false));
    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

    @Override
    public void onBindViewHolder(@NonNull RedDotHolder redDotHolder, final int i) {
        redDotHolder.view.setBackgroundResource(colors[i]);
        redDotHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select=i;
                if (onItemCallBack!=null){
                    onItemCallBack.onCallBack(getSelectColor());
                }
            }
        });
    }


    public class RedDotHolder extends RecyclerView.ViewHolder{
        TextView view;
        public RedDotHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.t_c);
        }
    }
    OnItemCallBack onItemCallBack;

    public void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    public interface OnItemCallBack{
        void onCallBack(int color);
    }
}
