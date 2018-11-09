package com.nongke.jindao.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.DaoLiTransferActivity;
import com.nongke.jindao.activity.DaoliRechargeActivity;
import com.nongke.jindao.activity.HelpFeedbackActivity;
import com.nongke.jindao.activity.MyAddressActivity;
import com.nongke.jindao.activity.MyBillActivity;
import com.nongke.jindao.activity.MyCommissionActivity;
import com.nongke.jindao.activity.MyInviterActivity;
import com.nongke.jindao.activity.MyProfileActivity;
import com.nongke.jindao.activity.OrderRecordActivity;
import com.nongke.jindao.activity.PromotionActivity;
import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.activity.WithdrawActivity;
import com.nongke.jindao.activity.WithdrawRecordActivity;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.event.LoginAccountEvent;
import com.nongke.jindao.base.event.UpdateUserInfoEvent;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.photopicker.ImageUtils;
import com.nongke.jindao.base.utils.PermissionUtil;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.mcontract.UserInfoContract;
import com.nongke.jindao.mpresenter.UserInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.my_daoli_recharge_layout)
    LinearLayout my_daoli_recharge_layout;
    @BindView(R.id.my_daoli_transfer_layout)
    LinearLayout my_daoli_transfer_layout;
    @BindView(R.id.my_bill_layout)
    LinearLayout my_bill_layout;
    @BindView(R.id.my_commission_layout)
    LinearLayout my_commission_layout;
    @BindView(R.id.my_inviter_layout)
    LinearLayout my_contact_layout;
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
    @BindView(R.id.custom_service_layout)
    LinearLayout custom_service_layout;
    @BindView(R.id.help_feedback_layout)
    LinearLayout help_feedback_layout;

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
    @BindView(R.id.tv_member_type)
    TextView tv_member_type;

    public static int PICK_PHOTO = 0;
    public ImageUtils imageUtils;

    @Override
    public void initData(Bundle bundle) {

        EventBus.getDefault().register(this);
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
        if (UserUtil.isLogined()) {
            ll_userinfo_profile_logined.setVisibility(View.VISIBLE);
            tv_user_profile_not_login.setVisibility(View.GONE);
            if (UserUtil.getUserInfo().rspBody == null) return;

            String photoUrl = UserUtil.getUserInfo().rspBody.img;
            if (photoUrl != null) {
                RequestOptions options = new RequestOptions().placeholder(R.drawable.user_default_photo);
                Glide.with(getActivity()).load(photoUrl).apply(options).into(iv_user_photo);
            }
            judgeLoginAndVip();

        } else {
            tv_user_profile_not_login.setVisibility(View.VISIBLE);
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
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void loadData() {
        refreshUserInfo();
    }


    @OnClick({R.id.iv_user_photo, R.id.my_daoli_recharge_layout, R.id.my_daoli_transfer_layout, R.id.my_bill_layout, R.id.my_commission_layout, R.id.my_withdraw_layout,
            R.id.my_withdraw_record_layout, R.id.my_profile_layout, R.id.my_promotion_layout, R.id.my_location_layout, R.id.my_order_layout, R.id.my_logout_layout,
            R.id.tv_vip_recharge, R.id.my_inviter_layout, R.id.custom_service_layout, R.id.help_feedback_layout})
    public void click(View view) {
        if (!UserUtil.isLogined()) {
            RegisterLoginActivity.startActivity(getActivity());
            Utils.showToast(getString(R.string.user_not_login), true);
            return;
        }
        switch (view.getId()) {
            case R.id.tv_vip_recharge:
                if (UserUtil.getUserInfo().rspBody.isVip == 0)
                    VipRechargeActivity.startActivity(getActivity());
                break;
            case R.id.my_daoli_recharge_layout:
                DaoliRechargeActivity.startActivity(getActivity());
                break;
            case R.id.my_daoli_transfer_layout:
                DaoLiTransferActivity.startActivity(getActivity());
                break;
            case R.id.my_bill_layout:
                MyBillActivity.startActivity(getActivity());
                break;
            case R.id.my_commission_layout:
                MyCommissionActivity.startActivity(getActivity());
                break;
            case R.id.my_inviter_layout:
                Bundle bundle = new Bundle();
                bundle.putString("uid", UserUtil.getUserInfo().rspBody.uid);
                MyInviterActivity.startActivity(getActivity(), bundle);
                break;
            case R.id.my_withdraw_layout:
                WithdrawActivity.startActivity(getActivity());

                break;
            case R.id.my_withdraw_record_layout:
                WithdrawRecordActivity.startActivity(getActivity());
                break;
            case R.id.my_profile_layout:
                MyProfileActivity.startActivity(getActivity());
                break;
            case R.id.my_promotion_layout:
                if (UserUtil.getUserInfo().rspBody.isVip != 1) {
                    Utils.showToast("你现在不是VIP会员，没有推广权限", false);
                    return;
                }
                PromotionActivity.startActivity(getActivity());
                break;
            case R.id.my_location_layout:
                MyAddressActivity.startActivity(getActivity());
                break;
            case R.id.my_order_layout:
                OrderRecordActivity.startActivity(getActivity());
                break;

            case R.id.my_logout_layout:

                if (UserUtil.getUserInfo() == null) {
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
                UserUtil.setUserInfo(null);
                UserUtil.setLogined(false);
                LoginAccountEvent accountEvent = new LoginAccountEvent();
                EventBus.getDefault().post(accountEvent);
                break;
            case R.id.iv_user_photo:
                if (!UserUtil.isLogined())
                    RegisterLoginActivity.startActivity(getActivity());
                else {
                    requestCameraPermission();

                }
                break;
            case R.id.custom_service_layout:
                toQQServer(getActivity());
                break;
            case R.id.help_feedback_layout:
                HelpFeedbackActivity.startActivity(getActivity());
                break;

        }

    }

    public void requestCameraPermission() {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.requestPermission(baseActivity, new PermissionUtil.RequestPermissionCallBack() {
            @Override
            public void granted() {
                imageUtils = new ImageUtils(getActivity());
                imageUtils.initChoosePop();
                imageUtils.popWinChoose.showAtLocation(subFragmentView, Gravity.BOTTOM, 0, 0); // 在底部显示
            }

            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.CAMERA});
    }

    public void uploadPhoto(String path) {
        mPresenter.uploadImg(path);
    }

    private void judgeLoginAndVip() {
        if (UserUtil.getUserInfo().rspBody.isVip == 1) {
            tv_member_type.setVisibility(View.GONE);
            tv_vip_recharge.setText("VIP会员");
        } else {
            tv_member_type.setVisibility(View.VISIBLE);
            tv_vip_recharge.setText(" 申请高级会员>>");
        }

        tv_user_phone_num.setText(UserUtil.getUserInfo().rspBody.phone);
        tv_user_inviter_phone_num.setText(UserUtil.getUserInfo().rspBody.inviterPhone);
        tv_user_balance.setText(UserUtil.getUserInfo().rspBody.money + "元");
        tv_user_commission.setText(UserUtil.getUserInfo().rspBody.commission + "元");
        tv_user_daoli_balance.setText(UserUtil.getUserInfo().rspBody.cardMoney + "元");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginAccountEvent accountEvent) {
        judgeLoginAndVip();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateUserInfoEvent userInfoEvent) {
        mPresenter.getUserInfo();
    }


    @Override
    public void showUserInfo(LoginResData loginResData) {
        UserUtil.setUserInfo(loginResData);
    }

    public static void toQQServer(Context context) {
        try {
            String qq = OnlineParamUtil.getParamResData().rspBody.custom_service_qq.content.trim();
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.tencent.mobileqq",
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            if (info != null) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
        }
    }

}
