package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.RechargeTabAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;

import java.util.ArrayList;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class CommodityFragment extends BaseMvpFragment {


    @Override
    public void initData( Bundle bundle) {

    }

    @Override
    public void initView() {



    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_commodity;
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
