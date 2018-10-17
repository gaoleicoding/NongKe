package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.AddressActivity;
import com.nongke.jindao.activity.DaoLiTransferActivity;
import com.nongke.jindao.activity.DaoliRechargeActivity;
import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.activity.UserProfileActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.activity.WithdrawActivity;
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
    @BindView(R.id.ll_userinfo_profile_logined)
    LinearLayout ll_userinfo_profile_logined;
    @BindView(R.id.iv_user_default)
    ImageView iv_user_default;
    @BindView(R.id.tv_vip_recharge)
    TextView tv_vip_recharge;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView() {
        if (UserUtils.isLogined()) ll_userinfo_profile_logined.setVisibility(View.VISIBLE);
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
            R.id.my_withdraw_record_layout, R.id.my_profile_layout, R.id.my_promotion_layout, R.id.my_location_layout, R.id.my_order_layout, R.id.my_logout_layout,
            R.id.iv_user_default,R.id.tv_vip_recharge})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_vip_recharge:
                VipRechargeActivity.startActivity(getActivity());
            break;
            case R.id.my_daoli_recharge_layout:
                DaoliRechargeActivity.startActivity(getActivity());
                break;
            case R.id.my_daoli_transfer_layout:
                DaoLiTransferActivity.startActivity(getActivity());
                break;
            case R.id.my_recharge_layout:

                break;
            case R.id.my_commission_layout:

                break;
            case R.id.my_withdraw_layout:
                WithdrawActivity.startActivity(getActivity());

                break;
            case R.id.my_withdraw_record_layout:

                break;
            case R.id.my_profile_layout:
                UserProfileActivity.startActivity(getActivity());
                break;
            case R.id.my_promotion_layout:

                break;
            case R.id.my_location_layout:
                AddressActivity.startActivity(getActivity());
                break;
            case R.id.my_order_layout:

                break;
            case R.id.my_logout_layout:

                break;
            case R.id.iv_user_default:
                RegisterLoginActivity.startActivity(getActivity());
                break;
            default:
                break;
        }

    }
}
