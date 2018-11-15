package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.BillResData.BillBody;
import com.nongke.jindao.base.mmodel.UserRecordResData;
import com.nongke.jindao.base.mmodel.UserRecordResData.UserRecordBody;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;

import okhttp3.internal.Util;


public class UserRecordAdapter extends RecyclerView.Adapter<UserRecordAdapter.MyViewHolder> {

    public Context context;

    public List<UserRecordBody> list;
    public float totalCommission;


    public UserRecordAdapter(Context context, List<UserRecordBody> list) {
        this.context = context;
        this.list = list;

    }

    public void setList(List<UserRecordBody> list) {
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            UserRecordBody body = list.get(i);
            if (body.amount > 0) totalCommission = totalCommission + body.amount;
        }
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        final UserRecordBody resData = list.get(position);
        holder.tv_bill_time.setText(Utils.ms2Date(Long.parseLong(resData.createTime)));

        holder.tv_bill_amount.setText(resData.amount + "元");
        holder.tv_bill_type.setText(resData.description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bill_time, tv_bill_amount, tv_bill_type;

        public MyViewHolder(View view) {
            super(view);
            tv_bill_time = view.findViewById(R.id.tv_bill_time);
            tv_bill_amount = view.findViewById(R.id.tv_bill_amount);
            tv_bill_type = view.findViewById(R.id.tv_bill_type);

        }
    }


}