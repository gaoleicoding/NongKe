package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.BillResData.BillBody;
import com.nongke.jindao.base.mmodel.PhoneRecordResData.PhoneRecordBody;
import com.nongke.jindao.base.utils.Utils;

import java.util.List;


public class MyBillAdapter extends RecyclerView.Adapter<MyBillAdapter.MyViewHolder> {

    public Context context;

    public List<BillBody> list;


    public MyBillAdapter(Context context, List<BillBody> list) {
        this.context = context;
        this.list = list;

    }

    public void setList(List<BillBody> list) {
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
        final BillBody resData = list.get(position);
        holder.tv_bill_time.setText( Utils.ms2Date(Long.parseLong(resData.time)));
        holder.tv_bill_amount.setText(resData.totalDesc+"元");
        holder.tv_bill_type.setText(resData.description);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bill_time,  tv_bill_amount, tv_bill_type;

        public MyViewHolder(View view) {
            super(view);
            tv_bill_time = view.findViewById(R.id.tv_bill_time);
            tv_bill_amount = view.findViewById(R.id.tv_bill_amount);
            tv_bill_type = view.findViewById(R.id.tv_bill_type);


        }
    }


}