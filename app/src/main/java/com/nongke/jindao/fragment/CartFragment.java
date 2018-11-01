package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.OrderActivity;
import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.adapter.CartAdapter;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.utils.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.event.ProductAmountEvent;
import com.nongke.jindao.event.ProductTotalPriceEvent;
import com.nongke.jindao.event.UpdateCartEvent;
import com.nongke.jindao.mcontract.CartContract;
import com.nongke.jindao.mpresenter.CartPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class CartFragment extends BaseMvpFragment<CartPresenter> implements CartContract.View {

    @BindView(R.id.tv_to_shop)
    TextView tv_to_shop;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.tv_product_total_price)
    TextView tv_product_total_price;
    @BindView(R.id.tv_product_order)
    TextView tv_product_order;
    @BindView(R.id.cart_recyclerview)
    RecyclerView cart_recyclerview;
    @BindView(R.id.ll_cart_empty)
    LinearLayout ll_cart_empty;
    @BindView(R.id.rl_to_pay)
    RelativeLayout rl_to_pay;
    @BindView(R.id.cb_product_select_all)
    CheckBox cb_product_select_all;
    boolean isEditingCart;


    private List<Product> cartDataList;
    private CartAdapter cartAdapter;

    @Override
    public void loadData() {
        if (UserUtil.isLogined()) {
            mPresenter.getCartProduct();
        } else {
            RegisterLoginActivity.startActivity(getActivity());
            Utils.showToast(getString(R.string.user_not_login), true);
            return;
        }
    }

    @Override
    public void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        initRecyclerView();
        cb_product_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    cartAdapter.selectAll(true, false);
                } else if (!b && !cartAdapter.clickAdapterCB) {
                    cartAdapter.selectAll(false, true);
                }
            }
        });
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

    @OnClick({R.id.tv_to_shop, R.id.tv_edit, R.id.tv_product_order})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_to_shop:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(2);
                break;
            case R.id.tv_product_order:
                if (isEditingCart) {
                    int selectListLength = cartAdapter.selectProductList.size();
                    if (selectListLength == 0) {
                        Utils.showToast("您还没选择要删除的商品", false);
                        return;
                    }

                    Log.d("CartAdapter", "selectListLength:" + selectListLength);
                    if (cartAdapter.isAllSelected()) {
                        mPresenter.clearCart();
                        cartAdapter.list.clear();
                        cartAdapter.selectProductList.clear();
                        cart_recyclerview.setVisibility(View.GONE);
                        ll_cart_empty.setVisibility(View.VISIBLE);
                        rl_to_pay.setVisibility(View.GONE);
                        tv_edit.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < selectListLength; i++) {
                            Product product = cartAdapter.selectProductList.get(i);
                            cartAdapter.list.remove(product);
                            mPresenter.deleteProduct(0, product.productId);
                        }
                        cartAdapter.selectProductList.clear();
                    }
                    cartAdapter.notifyDataSetChanged();
                    backFromManageCart();

                } else {
                    if (cartAdapter.selectProductList.size() == 0) {
                        Utils.showToast("你还没有选择要下单的商品", false);
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product_list", (Serializable) cartAdapter.selectProductList);
                    OrderActivity.startActivity(getActivity(), bundle);
                }
                break;
            case R.id.tv_edit:
                if (!isEditingCart) {
                    tv_edit.setText(getString(R.string.complete));
                    tv_product_order.setText(getString(R.string.delete));
                    tv_product_total_price.setVisibility(View.GONE);

                    isEditingCart = true;
                } else {
                    backFromManageCart();
                }
                break;
        }

    }

    public void backFromManageCart() {
        tv_edit.setText(getString(R.string.edit));
        tv_product_order.setText(getString(R.string.balance));
        tv_product_total_price.setVisibility(View.VISIBLE);
        cb_product_select_all.setChecked(false);
        cartAdapter.selectAll(false, true);
        tv_product_total_price.setText("合计：0元");
        isEditingCart = false;
    }

    @Override
    public void showProduct(ProductResData productResData) {
        if (productResData == null || productResData.rspBody == null) {
            cart_recyclerview.setVisibility(View.GONE);
            ll_cart_empty.setVisibility(View.VISIBLE);
            tv_edit.setVisibility(View.GONE);
            return;
        }
        final List<Product> newDataList = productResData.rspBody.list;
        if (newDataList.size() == 0) {
            cart_recyclerview.setVisibility(View.GONE);
            ll_cart_empty.setVisibility(View.VISIBLE);
            tv_edit.setVisibility(View.GONE);
            return;
        } else {
            cart_recyclerview.setVisibility(View.VISIBLE);
            ll_cart_empty.setVisibility(View.GONE);
            tv_edit.setVisibility(View.VISIBLE);
        }
        rl_to_pay.setVisibility(View.VISIBLE);
        cartAdapter.totalPrice = 0;
//        cartAdapter.selectAll(false,true);

        cartAdapter.setDataList(newDataList);
        if (!cartAdapter.isAllSelected()) {
            cb_product_select_all.setChecked(false);
        } else cb_product_select_all.setChecked(true);
    }

    private void initRecyclerView() {
        cartDataList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartDataList, "CartFragment", cb_product_select_all);
        cart_recyclerview.addItemDecoration(new SpacesItemDecoration(getActivity(), 8));

        cart_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //解决数据加载不完的问题
        cart_recyclerview.setNestedScrollingEnabled(false);
        cart_recyclerview.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        cart_recyclerview.setFocusable(false);
        cart_recyclerview.setAdapter(cartAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProductAmountEvent amountEvent) {
        mPresenter.updateProductAmount(amountEvent.amout, amountEvent.productId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProductTotalPriceEvent priceEvent) {
        tv_product_total_price.setText("合计：" + priceEvent.totalPrice + "元");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateCartEvent updateCartEvent) {
        mPresenter.getCartProduct();
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
