package com.nongke.jindao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.ProductResData.Product;

import com.nongke.jindao.base.thirdframe.glide.ImageLoader;
import com.nongke.jindao.base.utils.ScreenUtils;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    public Context context;
    int selectPosition = 0;
    OnItemClickListener listener;
    List<Product> list;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article_list, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (int) view.getTag();
                if (listener != null) {
                    listener.onItemClick(view, position);
                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Product productInfo = list.get(position);
        setImageWidthHeight(holder.item_project_list_iv);
        holder.item_project_list_title_tv.setText(productInfo.productName);
        holder.item_project_list_content_tv.setText(productInfo.productPrice + "元");
        ImageLoader.getInstance().load(context, productInfo.img, holder.item_project_list_iv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_project_list_title_tv, item_project_list_content_tv, item_project_list_time_tv, item_project_list_author_tv;
        ImageView item_project_list_iv;

        public MyViewHolder(View view) {
            super(view);
            item_project_list_iv = view.findViewById(R.id.item_project_list_iv);
            item_project_list_title_tv = view.findViewById(R.id.item_project_list_title_tv);
            item_project_list_content_tv = view.findViewById(R.id.item_project_list_content_tv);
//            item_project_list_author_tv = view.findViewById(R.id.item_project_list_author_tv);
//            item_project_list_time_tv = view.findViewById(R.id.item_project_list_time_tv);

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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