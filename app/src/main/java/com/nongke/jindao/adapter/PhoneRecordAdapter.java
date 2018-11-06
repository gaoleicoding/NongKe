package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.PhoneRecordResData.PhoneRecordBody;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;


public class PhoneRecordAdapter extends RecyclerView.Adapter<PhoneRecordAdapter.MyViewHolder> {

    public Context context;

    public List<PhoneRecordBody> list;


    public PhoneRecordAdapter(Context context, List<PhoneRecordBody> list) {
        this.context = context;
        this.list = list;

    }

    public void setList(List<PhoneRecordBody> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_phone_record, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        final PhoneRecordBody resData = list.get(position);
        holder.tv_recharge_time.setText( Utils.ms2Date(Long.parseLong(resData.createTime)));
        holder.tv_recharge_phone.setText(resData.phone);
        holder.tv_recharge_amount.setText(resData.totalMoney);
        holder.tv_recharge_discount_pay.setText(resData.discountMoney);
        holder.tv_recharge_status.setText(resData.status);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_recharge_time, tv_recharge_phone, tv_recharge_amount, tv_recharge_discount_pay,tv_recharge_status;

        public MyViewHolder(View view) {
            super(view);
            tv_recharge_time = view.findViewById(R.id.tv_recharge_time);
            tv_recharge_phone = view.findViewById(R.id.tv_recharge_phone);
            tv_recharge_amount = view.findViewById(R.id.tv_recharge_amount);
            tv_recharge_discount_pay = view.findViewById(R.id.tv_recharge_discount_pay);
            tv_recharge_status = view.findViewById(R.id.tv_recharge_status);


        }
    }


}