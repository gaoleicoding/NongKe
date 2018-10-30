package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.MessageResData.MessageBody;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    public Context context;

    public List<MessageBody> list;


    public MessageAdapter(Context context, List<MessageBody> list) {
        this.context = context;
        this.list = list;

    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        final MessageBody resData = list.get(position);
        holder.tv_message_title.setText(resData.title);
        holder.tv_message_content.setText(resData.content);
        holder.tv_message_time.setText(resData.createTime);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message_title, tv_message_content, tv_message_time;

        public MyViewHolder(View view) {
            super(view);
            tv_message_title = view.findViewById(R.id.tv_message_title);
            tv_message_content = view.findViewById(R.id.tv_message_content);
            tv_message_time = view.findViewById(R.id.tv_message_time);


        }
    }


}