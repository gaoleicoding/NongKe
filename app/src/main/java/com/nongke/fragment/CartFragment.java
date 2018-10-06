package com.nongke.fragment;

import android.os.Bundle;

import com.nongke.R;
import com.nongke.base.fragment.BaseMvpFragment;
import com.nongke.base.mpresenter.BasePresenter;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class CartFragment extends BaseMvpFragment {
    @Override
    protected void loadData() {

    }

    @Override
    public void initData( Bundle bundle) {

    }

    @Override
    public void initView() {

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
