package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.WithdrawRecordResData.WithdrawRecord;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;


public class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordAdapter.MyViewHolder> {

    public Context context;

    public List<WithdrawRecord> list;


    public WithdrawRecordAdapter(Context context, List<WithdrawRecord> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<WithdrawRecord> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        final WithdrawRecord resData = list.get(position);
        holder.tv_bill_time.setText(Utils.ms2Date(Long.parseLong(resData.applyTime)));
        holder.tv_bill_amount.setText(resData.amount + "元");
        if (resData.status == 0)
            holder.tv_bill_type.setText("审核中");
        if (resData.status == 1)
            holder.tv_bill_type.setText("审核通过");
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