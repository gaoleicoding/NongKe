package com.nongke.fragment;

import android.os.Bundle;

import com.nongke.R;
import com.nongke.base.fragment.BaseMvpFragment;
import com.nongke.base.mpresenter.BasePresenter;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment {


    @Override
    public void initData( Bundle bundle) {

    }

    @Override
    public void initView() {

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }
}