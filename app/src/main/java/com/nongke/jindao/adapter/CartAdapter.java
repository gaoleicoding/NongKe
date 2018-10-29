package com.nongke.jindao.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.ProductDetailActivity;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.thirdframe.glide.ImageLoader;
import com.nongke.jindao.base.utils.ScreenUtils;
import com.nongke.jindao.event.ProductAmountEvent;
import com.nongke.jindao.event.ProductTotalPriceEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    public Context context;
    int selectPosition = 0;
    OnItemClickListener listener;
    public List<Product> list;
    public List<Product> selectProductList;
    String fromWhere;
    //    int ammount = 1;
    String TAG = "CartAdapter";
    boolean isAllSelect;
    boolean isAllCancel;
    float totalPrice;

    public CartAdapter(Context context, List<Product> list, String fromWhere) {
        this.context = context;
        this.list = list;
        this.fromWhere = fromWhere;
        selectProductList=new ArrayList<Product>() ;
    }

    public void setDataList(List<Product> list) {
        this.list = list;
    }

    public void selectAll(boolean isAllSelect, boolean isAllCancel) {
        this.isAllSelect = isAllSelect;
        this.isAllCancel = isAllCancel;
        totalPrice = 0;
        notifyDataSetChanged();
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

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        if (!"CartFragment".equals(fromWhere))
            setClick(holder.itemView, position);
        final Product productInfo = list.get(position);
//        setImageWidthHeight(holder.iv_product);
        holder.tv_product_name.setText(productInfo.productName);
        holder.tv_product_price.setText(productInfo.productPrice + "元");
        holder.tv_product_ammount.setText(productInfo.amount + "");
        ImageLoader.getInstance().load(context, productInfo.img, holder.iv_product);
        if (isAllSelect) holder.cb_product_select.setChecked(true);
        if (isAllCancel) holder.cb_product_select.setChecked(false);
        if (holder.cb_product_select.isChecked()) {
            totalPrice = totalPrice + productInfo.productPrice * productInfo.amount;
            selectProductList.add(productInfo);
        }
        updateTotalPrice(totalPrice);
        holder.cb_product_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    productInfo.isChecked = true;
                } else {
                    productInfo.isChecked = false;
                }
                holder.cb_product_select.post(new Runnable() {
                    @Override
                    public void run() {
                        totalPrice = 0;
                        isAllSelect=false;
                        isAllCancel=false;
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.iv_product_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productInfo.amount > 1) {
                    productInfo.amount = productInfo.amount - 1;
                    holder.tv_product_ammount.setText(productInfo.amount + "");
                    updateAmount(productInfo, productInfo.amount, list.get(position).productId);
                }

            }
        });
        holder.iv_product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productInfo.amount = productInfo.amount + 1;
                holder.tv_product_ammount.setText(productInfo.amount + "");
                updateAmount(productInfo, productInfo.amount, list.get(position).productId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name, tv_product_price, tv_product_ammount;
        ImageView iv_product, iv_product_reduce, iv_product_add;
        CheckBox cb_product_select;

        public MyViewHolder(View view) {
            super(view);
            cb_product_select = view.findViewById(R.id.cb_product_select);
            iv_product = view.findViewById(R.id.iv_product);
            tv_product_name = view.findViewById(R.id.tv_product_name);
            tv_product_price = view.findViewById(R.id.tv_product_price);
            iv_product_reduce = view.findViewById(R.id.iv_product_reduce);
            iv_product_add = view.findViewById(R.id.iv_product_add);
            tv_product_ammount = view.findViewById(R.id.tv_product_ammount);
            cb_product_select.setChecked(false);
//            cb_product_select.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean checked=cb_product_select.isChecked();
//                    Log.d(TAG,"checked："+checked);
//                    cb_product_select.setChecked(!checked);
//                }
//            });


        }
    }

    private void updateAmount(Product productInfo, int amount, int productId) {
        productInfo.amount = amount;
        ProductAmountEvent amountEvent = new ProductAmountEvent();
        amountEvent.amout = amount;
        amountEvent.productId = productId;
        EventBus.getDefault().post(amountEvent);
//        notifyDataSetChanged();
    }

    private void updateTotalPrice(float totalPrice) {
        ProductTotalPriceEvent priceEvent = new ProductTotalPriceEvent();
        priceEvent.totalPrice = totalPrice;
        EventBus.getDefault().post(priceEvent);
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