package com.nongke.jindao.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.UserAddressActivity;
import com.nongke.jindao.activity.DaoLiTransferActivity;
import com.nongke.jindao.activity.DaoliRechargeActivity;
import com.nongke.jindao.activity.PromotionActivity;
import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.activity.UserProfileActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.activity.WithdrawActivity;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.photopicker.ImageUtils;
import com.nongke.jindao.base.utils.PermissionUtil;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mpresenter.RegisterLoginPresenter;
import com.nongke.jindao.base.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment<RegisterLoginPresenter> {

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


    @BindView(R.id.iv_user_photo)
    public ImageView iv_user_photo;
    @BindView(R.id.tv_vip_recharge)
    TextView tv_vip_recharge;
    @BindView(R.id.tv_user_phone_num)
    TextView tv_user_phone_num;
    @BindView(R.id.tv_user_inviter_phone_num)
    TextView tv_user_inviter_phone_num;
    @BindView(R.id.tv_user_balance)
    TextView tv_user_balance;
    @BindView(R.id.tv_user_commission)
    TextView tv_user_commission;
    @BindView(R.id.tv_user_daoli_balance)
    TextView tv_user_daoli_balance;
    @BindView(R.id.tv_user_profile_not_login)
    TextView tv_user_profile_not_login;
    public static int PICK_PHOTO=0;
    public   ImageUtils imageUtils;

    @Override
    public void initData(Bundle bundle) {
        refreshUserInfo();
    }

    @Override
    public void initView() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 系统创建 Fragment 的时候回调，介于 onAttach() 和 onCreateView() 之间
     * 一般用于初始化一些数据
     * 值得注意的是，此时 Activity 还在创建中，因此不能在执行一些跟 Activity UI 相关的操作
     * 否则，会出现一些难以预料的问题，比如：NullPointException
     * 如果要对 Activity 上的 UI 进行操作，建议在 onActivityCreated() 中操作
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void refreshUserInfo() {
        if (UserUtils.isLogined()) {
            ll_userinfo_profile_logined.setVisibility(View.VISIBLE);
            tv_user_profile_not_login.setVisibility(View.GONE);
            tv_user_phone_num.setText(UserUtils.getUserInfo().rspBody.phone);
            tv_user_inviter_phone_num.setText(UserUtils.getUserInfo().rspBody.inviterPhone);
            tv_user_balance.setText(UserUtils.getUserInfo().rspBody.money + "");
            tv_user_commission.setText(UserUtils.getUserInfo().rspBody.commission + "");
            tv_user_daoli_balance.setText(UserUtils.getUserInfo().rspBody.cardMoney + "");
            String photoUrl = UserUtils.getUserInfo().rspBody.img;
            if (photoUrl != null)
                Glide.with(getActivity()).load(photoUrl).into(iv_user_photo);
            else iv_user_photo.setImageResource(R.drawable.user_photo);
        } else {
            tv_user_profile_not_login.setVisibility(View.VISIBLE);
            iv_user_photo.setImageResource(R.drawable.user_default_photo);
        }

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void reload() {
    }

    @Override
    public RegisterLoginPresenter initPresenter() {
        return new RegisterLoginPresenter();
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.iv_user_photo, R.id.my_daoli_recharge_layout, R.id.my_daoli_transfer_layout, R.id.my_recharge_layout, R.id.my_commission_layout, R.id.my_withdraw_layout,
            R.id.my_withdraw_record_layout, R.id.my_profile_layout, R.id.my_promotion_layout, R.id.my_location_layout, R.id.my_order_layout, R.id.my_logout_layout,
            R.id.tv_vip_recharge})
    public void click(View view) {
        if (UserUtils.getUserInfo() == null) {
            RegisterLoginActivity.startActivity(getActivity());
            Utils.showToast(getString(R.string.user_not_login), true);
            return;
        }
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
                PromotionActivity.startActivity(getActivity());
                break;
            case R.id.my_location_layout:
                UserAddressActivity.startActivity(getActivity());
                break;
            case R.id.my_order_layout:

                break;
            case R.id.my_logout_layout:

                if (UserUtils.getUserInfo() == null) {
                    Utils.showToast(getString(R.string.user_not_login), true);
                    return;
                }

                ll_userinfo_profile_logined.setVisibility(View.GONE);
                tv_user_profile_not_login.setVisibility(View.VISIBLE);
                iv_user_photo.setImageResource(R.drawable.user_default_photo);
                SharedPreferencesUtils.clear(getActivity(), "phone_num");
                SharedPreferencesUtils.clear(getActivity(), "password");
                mPresenter.getLogoutData();
                RegisterLoginActivity.startActivity(getActivity());
                UserUtils.setUserInfo(null);
                UserUtils.setLogined(false);
                break;
            case R.id.iv_user_photo:
                if (!UserUtils.isLogined())
                    RegisterLoginActivity.startActivity(getActivity());
                else {
                    requestCameraPermission();

                }
                break;
            default:
                break;
        }

    }
    public void requestCameraPermission(){
        BaseActivity baseActivity=(BaseActivity)getActivity();
        baseActivity.requestPermission(baseActivity,new PermissionUtil.RequestPermissionCallBack() {
            @Override
            public void granted() {
                imageUtils=new ImageUtils(getActivity());
                imageUtils.initChoosePop();
                imageUtils.popWinChoose.showAtLocation(subFragmentView, Gravity.BOTTOM, 0, 0); // 在底部显示
            }

            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.CAMERA});
    }
    public void uploadPhoto(String path){
        mPresenter.uploadImg(path);
    }
}
