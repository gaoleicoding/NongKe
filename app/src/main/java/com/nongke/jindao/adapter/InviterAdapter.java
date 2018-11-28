package com.nongke.jindao.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.MyInviterActivity;
import com.nongke.jindao.base.mmodel.MyInviterResData.InviterBody;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;


public class InviterAdapter extends RecyclerView.Adapter<InviterAdapter.MyViewHolder> {

    public Context context;

    public List<InviterBody> list;


    public InviterAdapter(Context context, List<InviterBody> list) {
        this.context = context;
        this.list = list;

    }

    public void setList(List<InviterBody> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_inviter, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        final InviterBody resData = list.get(position);
        holder.tv_inviter_account.setText("账号：" + resData.phone);
        holder.tv_inviter_vip.setText("（"+resData.isVip+"）");
        holder.tv_inviter_commission.setText("佣金：" + resData.commission+"元");
        holder.tv_inviter_time.setText("注册时间：" + Utils.ms2Date(resData.createTime));
        if (resData.img != null) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.user_default_photo);
            Glide.with(context).load(resData.img).apply(options).into(holder.iv_user_photo);
        }
        holder.iv_user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyInviterActivity.level < 3) {
                    MyInviterActivity.level = MyInviterActivity.level + 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", resData.invitedUid);
                    bundle.putString("phone", resData.phone);
                    MyInviterActivity.startActivity(context, bundle);

                } else {
                    Utils.showToast("你最多只能查看3级", false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_inviter_account, tv_inviter_vip, tv_inviter_time, tv_inviter_commission;
        ImageView iv_user_photo;

        public MyViewHolder(View view) {
            super(view);
            iv_user_photo = view.findViewById(R.id.iv_user_photo);
            tv_inviter_account = view.findViewById(R.id.tv_inviter_account);
            tv_inviter_vip = view.findViewById(R.id.tv_inviter_vip);
            tv_inviter_time = view.findViewById(R.id.tv_inviter_time);
            tv_inviter_commission = view.findViewById(R.id.tv_inviter_commission);


        }
    }


}