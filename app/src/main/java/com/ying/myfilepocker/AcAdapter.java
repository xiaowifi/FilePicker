package com.ying.myfilepocker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                context.startActivity(new Intent(context, beans.get(i).a));
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
