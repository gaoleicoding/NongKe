package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.LoginRegisterActivity;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment {

    @BindView(R.id.my_daoli_recharge_layout)
    LinearLayout my_daoli_recharge_layout;
    @BindView(R.id.my_daoli_transfer_layout)
    LinearLayout my_daoli_transfer_layout;
    @BindView(R.id.my_recharge_layout)
    LinearLayout my_recharge_layout;
    @BindView(R.id.my_commission_layout)
    LinearLayout my_commission_layout;
    @BindView(R.id.my_withdraw_layout)
    LinearLayout my_withdraw_layout;
    @BindView(R.id.my_withdraw_record_layout)
    LinearLayout my_withdraw_record_layout;
    @BindView(R.id.my_profile_layout)
    LinearLayout my_profile_layout;
    @BindView(R.id.my_promotion_layout)
    LinearLayout my_promotion_layout;
    @BindView(R.id.my_location_layout)
    LinearLayout my_location_layout;
    @BindView(R.id.my_order_layout)
    LinearLayout my_order_layout;
    @BindView(R.id.my_logout_layout)
    LinearLayout my_logout_layout;

    @Override
    public void initData(Bundle bundle) {

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


    @OnClick({R.id.my_daoli_recharge_layout, R.id.my_daoli_transfer_layout, R.id.my_recharge_layout, R.id.my_commission_layout, R.id.my_withdraw_layout,
            R.id.my_withdraw_record_layout, R.id.my_profile_layout, R.id.my_promotion_layout, R.id.my_location_layout, R.id.my_order_layout, R.id.my_logout_layout})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.my_daoli_recharge_layout:
                if (!UserUtils.isLogined()) LoginRegisterActivity.startActivity(getActivity());
                break;
            case R.id.my_daoli_transfer_layout:

                break;
            case R.id.my_recharge_layout:

                break;
            case R.id.my_commission_layout:

                break;
            case R.id.my_withdraw_layout:

                break;
            case R.id.my_withdraw_record_layout:

                break;
            case R.id.my_profile_layout:

                break;
            case R.id.my_promotion_layout:

                break;
            case R.id.my_location_layout:

                break;
            case R.id.my_order_layout:

                break;
            case R.id.my_logout_layout:

                break;
            default:
                break;
        }

    }
}
