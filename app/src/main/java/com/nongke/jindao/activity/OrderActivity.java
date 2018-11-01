package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.OrderProductAdapter;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mpresenter.ProductDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class OrderActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_product_total_price)
    TextView tv_product_total_price;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.order_product_recyclerview)
    RecyclerView order_product_recyclerview;

    private List<Product> orderProductList;
    private OrderProductAdapter orderProductAdapter;
    String TAG = "OrderActivity";
    float totalPrice;


    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("params", bundle);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.order_detail));
        iv_back.setVisibility(View.VISIBLE);
        orderProductList = (List<Product>) bundle.getSerializable("product_list");
        for (int i = 0; i < orderProductList.size(); i++) {
            Product productInfo = orderProductList.get(i);
            totalPrice = totalPrice + productInfo.productPrice * productInfo.amount;
        }
        tv_product_total_price.setText("合计：" + totalPrice + "元");
        LogUtil.d(TAG, "orderProductList.size():" + orderProductList.size());
        initRecyclerView();
    }

    @Override
    public ProductDetailPresenter initPresenter() {
        return new ProductDetailPresenter();
    }

    @Override
    protected void loadData() {


    }

    @OnClick({R.id.iv_back})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_pay:

                break;


        }
    }


    private void initRecyclerView() {
        orderProductAdapter = new OrderProductAdapter(this, orderProductList);
        order_product_recyclerview.addItemDecoration(new SpacesItemDecoration(this, 2));

        order_product_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //解决数据加载不完的问题
        order_product_recyclerview.setNestedScrollingEnabled(false);
        order_product_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        order_product_recyclerview.setFocusable(false);
        order_product_recyclerview.setAdapter(orderProductAdapter);
    }


}
