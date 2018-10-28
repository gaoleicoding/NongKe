package com.nongke.jindao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.thirdframe.glide.ImageLoader;
import com.nongke.jindao.base.utils.ScreenUtils;


public class ProductDetailImgAdapter extends RecyclerView.Adapter<ProductDetailImgAdapter.MyViewHolder> {

    public Context context;
    int selectPosition = 0;
    OnItemClickListener listener;
    String[] imgArray;
    String TAG = "ProductDetailActivity";

    public ProductDetailImgAdapter(Context context, String[] imgArray) {
        this.context = context;
        this.imgArray = imgArray;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_img, null);
        MyViewHolder holder = new MyViewHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int position = (int) view.getTag();
//                if (listener != null) {
//                    listener.onItemClick(view, position);
//                }
//            }
//        });
        return holder;
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        final String url = imgArray[position];
        Log.d(TAG, "img---------------" + url);
//        setImageWidthHeight(holder.item_project_list_iv);
        holder.item_tv_detail.setText(url);
        ImageLoader.getInstance().load(context, url, holder.item_iv_detail);
//        ViewTreeObserver vto = holder.item_tv_detail.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//
//                ImageLoader.getInstance().asBitmap().load(context,url,holder.item_iv_detail);
//                return true;
//            }
//
//        });

//        holder.item_iv_detail.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                ImageLoader.getInstance().load(context,url,holder.item_iv_detail);
//
//            }
//        },100);


//        Glide.with(context).load(url).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                holder.item_iv_detail.setBackgroundDrawable(resource);
//                ImageLoader.getInstance().load(context,url,holder.item_iv_detail);

//                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                holder.item_iv_detail.setScaleType(ImageView.ScaleType.FIT_XY);
////                }
//                ViewGroup.LayoutParams params = holder.item_iv_detail.getLayoutParams();
//                int vw = holder.item_iv_detail.getWidth() - holder.item_iv_detail.getPaddingLeft() - holder.item_iv_detail.getPaddingRight();
//                float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                params.height = vh + holder.item_iv_detail.getPaddingTop() + holder.item_iv_detail.getPaddingBottom();
//                holder.item_iv_detail.setLayoutParams(params);
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return imgArray.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //        TextView item_project_list_title_tv, item_project_list_content_tv, item_project_list_time_tv, item_project_list_author_tv;
        ImageView item_iv_detail;
        TextView item_tv_detail;

        public MyViewHolder(View view) {
            super(view);
            item_iv_detail = view.findViewById(R.id.item_iv_detail);
            item_tv_detail = view.findViewById(R.id.item_tv_detail);


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