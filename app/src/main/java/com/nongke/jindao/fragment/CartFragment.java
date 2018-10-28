package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.adapter.CartAdapter;
import com.nongke.jindao.adapter.divider.DividerItemDecoration;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.mcontract.CartContract;
import com.nongke.jindao.mpresenter.CartPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nongke.jindao.adapter.divider.DividerItemDecoration.HORIZONTAL_LIST;
import static com.nongke.jindao.adapter.divider.DividerItemDecoration.VERTICAL_LIST;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class CartFragment extends BaseMvpFragment<CartPresenter> implements CartContract.View {

    @BindView(R.id.tv_to_shop)
    TextView tv_to_shop;
    @BindView(R.id.cart_recyclerview)
    RecyclerView cart_recyclerview;
    @BindView(R.id.ll_cart_empty)
    LinearLayout ll_cart_empty;


    private List<Product> cartDataList;
    private CartAdapter cartAdapter;

    @Override
    public void loadData() {
        mPresenter.getCartProduct();
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    public void reload() {
    }

    @Override
    public CartPresenter initPresenter() {
        return new CartPresenter();
    }

    @OnClick({R.id.tv_to_shop})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_to_shop:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(2);
            default:
                break;
        }

    }

    @Override
    public void showProduct(ProductResData productResData) {
        if (productResData == null || productResData.rspBody == null) {
            cart_recyclerview.setVisibility(View.GONE);
            ll_cart_empty.setVisibility(View.VISIBLE);
            return;
        }
        final List<Product> newDataList = productResData.rspBody.list;
        if (newDataList.size() == 0) {
            cart_recyclerview.setVisibility(View.GONE);
            ll_cart_empty.setVisibility(View.VISIBLE);
            return;
        }

        cartAdapter.setDataList(newDataList);
        cartAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        cartDataList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartDataList, "CartFragment");
        cart_recyclerview.addItemDecoration(new SpacesItemDecoration(10));

        cart_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //解决数据加载不完的问题
        cart_recyclerview.setNestedScrollingEnabled(false);
        cart_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        cart_recyclerview.setFocusable(false);
        cart_recyclerview.setAdapter(cartAdapter);
    }


}
