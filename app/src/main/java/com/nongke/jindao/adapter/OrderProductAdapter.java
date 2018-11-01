package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.thirdframe.glide.ImageLoader;

import java.util.List;


public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {

    public Context context;

    public List<Product> list;


    public OrderProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;

    }

    public void setDataList(List<Product> list) {
        this.list = list;

        notifyDataSetChanged();

    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_product, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);

        final Product productInfo = list.get(position);
        if(productInfo!=null) {
            holder.tv_product_name.setText(productInfo.productName);
            holder.tv_product_price.setText(productInfo.productPrice + "元");
            holder.tv_product_ammount.setText("x " + productInfo.amount);
            ImageLoader.getInstance().load(context, productInfo.img, holder.iv_product);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_product;
        TextView tv_product_name, tv_product_price, tv_product_ammount;

        public MyViewHolder(View view) {
            super(view);
            iv_product = view.findViewById(R.id.iv_product);
            tv_product_name = view.findViewById(R.id.tv_product_name);
            tv_product_price = view.findViewById(R.id.tv_product_price);
            tv_product_ammount = view.findViewById(R.id.tv_product_ammount);


        }
    }


}