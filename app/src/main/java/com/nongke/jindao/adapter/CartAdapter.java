package com.nongke.jindao.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.ProductDetailActivity;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.thirdframe.glide.ImageLoader;
import com.nongke.jindao.base.utils.ScreenUtils;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements View.OnClickListener {

    public Context context;
    int selectPosition = 0;
    OnItemClickListener listener;
    List<Product> list;
    String fromWhere;
    int ammount = 1;

    public CartAdapter(Context context, List<Product> list, String fromWhere) {
        this.context = context;
        this.list = list;
        this.fromWhere = fromWhere;
    }

    public void setDataList(List<Product> list) {
        this.list = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int position = (int) view.getTag();
//                if (listener != null) {
//                    listener.onItemClick(view, position);
//                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (!"CartFragment".equals(fromWhere))
            setClick(holder.itemView, position);
        Product productInfo = list.get(position);
//        setImageWidthHeight(holder.iv_product);
        holder.tv_product_name.setText(productInfo.productName);
        holder.tv_product_price.setText(productInfo.productPrice + "元");
        ImageLoader.getInstance().load(context, productInfo.img, holder.iv_product);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_product_select:

                break;
            case R.id.iv_product_add:
                ammount = ammount + 1;
//                tv_product_ammount.setText(ammount + "");
                break;
            case R.id.iv_product_reduce:
                if (ammount > 1) {
                    ammount = ammount - 1;
//                    tv_product_ammount.setText(ammount + "");
                }
                break;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name, tv_product_price, tv_product_ammount;
        ImageView iv_product_select, iv_product, iv_product_reduce, iv_product_add;

        public MyViewHolder(View view) {
            super(view);
            iv_product_select = view.findViewById(R.id.iv_product_select);
            iv_product = view.findViewById(R.id.iv_product);
            tv_product_name = view.findViewById(R.id.tv_product_name);
            tv_product_price = view.findViewById(R.id.tv_product_price);
            iv_product_reduce = view.findViewById(R.id.iv_product_reduce);
            iv_product_add = view.findViewById(R.id.iv_product_add);
            tv_product_ammount = view.findViewById(R.id.tv_product_ammount);
            iv_product_select.setOnClickListener(CartAdapter.this);
            iv_product_reduce.setOnClickListener(CartAdapter.this);
            iv_product_add.setOnClickListener(CartAdapter.this);

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void setClick(View itemView, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", list.get(position));

                ProductDetailActivity.startActivity(context, bundle);
            }
        });
    }

    private void setImageWidthHeight(ImageView imageView) {
        int imageWidth = CustomApplication.screenWidth - ScreenUtils.dp2px(context, 60) / 2;
        int imageHeight = CustomApplication.screenHeight - ScreenUtils.dp2px(context, 60) / 2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height = getImageWidth();
        params.width = getImageWidth();
        imageView.setLayoutParams(params);

    }


    public int getImageWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //130是在xml文件中那一排recyclerview之间的间距加上recyclerview和父控件的间距的和
        int imageWidth = (int) (width - 20 * density) / 2;
        return imageWidth;
    }
}