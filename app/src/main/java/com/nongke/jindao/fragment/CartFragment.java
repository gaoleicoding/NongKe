package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class CartFragment extends BaseMvpFragment {

    @BindView(R.id.tv_to_shop)
    TextView tv_to_shop;
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

    @OnClick({R.id.tv_to_shop})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_to_shop:
                MainActivity mainActivity=(MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(2);
            default:
                break;
        }

    }
}
