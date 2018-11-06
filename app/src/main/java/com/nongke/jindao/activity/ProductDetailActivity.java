package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongke.jindao.R;
import com.nongke.jindao.adapter.ProductDetailImgAdapter;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mpresenter.ProductDetailPresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class ProductDetailActivity extends BaseMvpActivity<ProductDetailPresenter> {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_price)
    TextView tv_product_price;
    @BindView(R.id.tv_product_ammount)
    TextView tv_product_ammount;
    @BindView(R.id.tv_product_sales)
    TextView tv_product_sales;
    @BindView(R.id.tv_product_stock)
    TextView tv_product_stock;
    @BindView(R.id.tv_product_desc)
    TextView tv_product_desc;
    @BindView(R.id.tv_product_order)
    TextView tv_product_order;
    @BindView(R.id.tv_product_add_cart)
    TextView tv_product_add_cart;
    @BindView(R.id.iv_product_add)
    ImageView iv_product_add;
    @BindView(R.id.iv_product_reduce)
    ImageView iv_product_reduce;
    @BindView(R.id.recyclerview_detail_img)
    RecyclerView recyclerview_detail_img;
    //    @BindView(R.id.layout_scroll)
//    ScrollView layout_scroll;
    int ammount = 1, productId;
    String TAG = "ProductDetailActivity";
    Product product;

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("params", bundle);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.product_detail));
        iv_back.setVisibility(View.VISIBLE);
        product = (Product) bundle.getSerializable("product");
        initData(product);
    }

    @Override
    public ProductDetailPresenter initPresenter() {
        return new ProductDetailPresenter();
    }

    @Override
    protected void loadData() {
//        layout_scroll.scrollTo(0,0);

    }

    @OnClick({R.id.iv_product_add, R.id.iv_product_reduce, R.id.tv_product_add_cart, R.id.tv_product_order})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_product_add:
                ammount = ammount + 1;
                tv_product_ammount.setText(ammount + "");
                break;
            case R.id.iv_product_reduce:
                if (ammount > 1) {
                    ammount = ammount - 1;
                    tv_product_ammount.setText(ammount + "");
                }
                product.amount = ammount;
                break;
            case R.id.tv_product_add_cart:
                if (ammount > product.stockAmount) {
                    Utils.showToast("你选择的数量大于库存，请重新选择", false);
                    return;
                }
                product.amount = ammount;
                mPresenter.saveCart(ammount, productId);

                break;
            case R.id.tv_product_order:
                if (product.stockAmount <= 0) {
                    return;
                }
                if (!UserUtil.isLogined()) {
                    RegisterLoginActivity.startActivity(this);
                    Utils.showToast(getString(R.string.user_not_login), true);
                    return;
                }
                List<Product> list = new ArrayList<>();
                list.add(product);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product_list", (Serializable) list);
                OrderActivity.startActivity(ProductDetailActivity.this, bundle);


                break;
        }
    }

    private void initData(Product product) {

        Log.d(TAG, "product.coverImg：" + product.coverImg);
        Log.d(TAG, "product.detailImgs：" + product.detailImgs);

        productId = product.productId;
        tv_product_name.setText(product.productName);
        tv_product_price.setText(product.productPrice + " 元");
        tv_product_ammount.setText(ammount + "");
        tv_product_sales.setText("销量：" + product.salesAmount);
        tv_product_stock.setText("库存：" + product.stockAmount + "");
        tv_product_desc.setText(product.detail);
        showBannerList(product.coverImg);
        initRecyclerView(product.detailImgs);
        product.amount = 1;
        if (product.stockAmount <= 0) {
            tv_product_order.setText(getString(R.string.product_sold_out));
            tv_product_order.setBackgroundColor(getResources().getColor(R.color.color_9b9b9b));
            tv_product_add_cart.setVisibility(View.GONE);
        }
    }

    public void showBannerList(String coverImg) {
        String[] covers = coverImg.split(",");
//        final List<Product> bannerList = productResData.rspBody;
//        LogUtil.d("bannerList.size():" + bannerList.size());
        List imageList = new ArrayList();
//        List titleList = new ArrayList();
//        final List orderList = new ArrayList();
        int size = covers.length;

        for (int i = 0; i < size; i++) {
            imageList.add(covers[i]);
//            titleList.add(bannerList.get(i).productName);
//            orderList.add(bannerList.get(i).productId);
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(ProductDetailActivity.this).load(path).into(imageView);
            }
        });

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        //设置banner动画效果
        //        Tansformer.CubeIn
        //        Transformer.CubeOut
        //        Transformer.DepthPage
        //        Transformer.FlipHorizontal
        //        Transformer.FlipVertical
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(imageList);//设置图片源
//        banner.setBannerTitles(titleList);//设置标题源

        banner.start();

    }

    private void initRecyclerView(String detailImgs) {
        ProductDetailImgAdapter feedArticleAdapter = new ProductDetailImgAdapter(this, detailImgs.split(","));
//        recyclerview_detail_img.addItemDecoration(new GridItemSpaceDecoration(2, ScreenUtils.dp2px(getActivity(), 10), false));

        recyclerview_detail_img.setLayoutManager(new LinearLayoutManager(this) {
                                                     @Override
                                                     public boolean canScrollVertically() {
                                                         //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                                                         //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                                                         return false;
                                                     }
                                                 }
        );
        //解决数据加载不完的问题
        recyclerview_detail_img.setNestedScrollingEnabled(false);
        recyclerview_detail_img.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerview_detail_img.setFocusable(false);

        recyclerview_detail_img.setAdapter(feedArticleAdapter);
    }


}
