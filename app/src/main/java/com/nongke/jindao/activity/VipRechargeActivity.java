package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.pay.wxpay.WXPayUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.view.TitleView;
import com.nongke.jindao.mcontract.RechargeContract;
import com.nongke.jindao.mpresenter.RechargePresenter;
import com.nongke.jindao.view.PayView;

import butterknife.BindView;
import butterknife.OnClick;


public class VipRechargeActivity extends BaseMvpActivity<RechargePresenter> implements RechargeContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.layout_title)
    TitleView layout_title;
    @BindView(R.id.tv_vip_right_desc)
    TextView tv_vip_right_desc;
    @BindView(R.id.tv_vip_price)
    TextView tv_vip_price;
    @BindView(R.id.tv_vip_origin_price)
    TextView tv_vip_origin_price;
    @BindView(R.id.tv_vip_origin_price_gift)
    TextView tv_vip_origin_price_gift;
    @BindView(R.id.tv_vip_price_to_user)
    TextView tv_vip_price_to_user;

    @BindView(R.id.tv_vip_contract)
    TextView tv_vip_contract;
    @BindView(R.id.tv_vip_contract_content)
    TextView tv_vip_contract_content;
    @BindView(R.id.cb_vip_contract)
    CheckBox cb_vip_contract;
    @BindView(R.id.ll_pay_view)
    PayView pay_view;

    boolean isInContract;
    public String TAG = "VipRechargeActivity";


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VipRechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_recharge;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.vip_member));
        iv_back.setVisibility(View.VISIBLE);
        if (OnlineParamUtil.paramResData == null || OnlineParamUtil.paramResData.rspBody == null)
            return;
        tv_vip_right_desc.setText(OnlineParamUtil.paramResData.rspBody.vip_right_desc.content);
        if (OnlineParamUtil.paramResData != null && OnlineParamUtil.paramResData.rspBody != null) {
            int vipPrice = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.vip_price.content);
            int vipToUserMoney = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.vip_to_user_money.content);
            int originMoney = Utils.stringToInt(OnlineParamUtil.paramResData.rspBody.vip_original_price.content);
//            String giftBoolean = OnlineParamUtil.paramResData.rspBody.vip_recharge_gift_boolean.content;
//            String gift = OnlineParamUtil.paramResData.rspBody.vip_recharge_gift.content;
            String vip_recharge_price = getResources().getString(R.string.vip_recharge_price);
            String vip_recharge_price_to_user = getResources().getString(R.string.vip_recharge_price_to_user);
            String vip_recharge_origin_price = getResources().getString(R.string.vip_recharge_origin_price);
            String recharge_price = String.format(vip_recharge_price, vipPrice);
            String recharge_price_to_user = String.format(vip_recharge_price_to_user, vipToUserMoney);
            String recharge_origin_price = String.format(vip_recharge_origin_price, originMoney);
            tv_vip_price.setText(recharge_price);

            if (vipPrice < originMoney) {
                tv_vip_origin_price.setVisibility(View.VISIBLE);
                tv_vip_origin_price.setText(recharge_origin_price);
                tv_vip_origin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

            }
//            if ("true".equals(giftBoolean.trim())) {
//                tv_vip_origin_price_gift.setVisibility(View.VISIBLE);
//                tv_vip_origin_price_gift.setText(gift);
//            }
        }
    }


    @OnClick({ R.id.iv_back, R.id.tv_pay, R.id.tv_vip_contract, R.id.tv_vip_contract_content})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                if (isInContract) {
                    isInContract = false;
                    tv_vip_contract_content.setVisibility(View.GONE);
                    title.setText(getResources().getString(R.string.vip_member));
                    layout_title.setVisibility(View.VISIBLE);
                } else finish();
                break;

            case R.id.tv_pay:
                if (UserUtil.getUserInfo().rspBody.isVip == 1) {
                    Utils.showToast("你已经是VIP会员", false);
                    return;
                }
                if (!cb_vip_contract.isChecked()) {
                    Utils.showToast(getResources().getString(R.string.read_agree_contract), false);
                    return;
                }

                mPresenter.recharge(1, pay_view.getPayType(), Utils.stringToFloat(OnlineParamUtil.paramResData.rspBody.vip_price.content), Utils.stringToFloat(OnlineParamUtil.paramResData.rspBody.vip_price.content));
                break;
            case R.id.tv_vip_contract:
                if(OnlineParamUtil.paramResData==null)return;
                isInContract = true;
                tv_vip_contract_content.setText(OnlineParamUtil.paramResData.rspBody.vip_contract.content);
                tv_vip_contract_content.setVisibility(View.VISIBLE);
                title.setText(getResources().getString(R.string.vip_contract_desc));
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isInContract) {
                isInContract = false;
                tv_vip_contract_content.setVisibility(View.GONE);
                title.setText(getResources().getString(R.string.vip_member));
                layout_title.setVisibility(View.VISIBLE);
            } else finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public RechargePresenter initPresenter() {
        return new RechargePresenter();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showRechargeRes(RechargeResData rechargeResData) {
        final String paySign = rechargeResData.rspBody.paySign;
        Log.d(TAG, "paySign:" + paySign);

        if (3 == pay_view.getPayType())AliPayUtil.pay(this, paySign);
        if (4 == pay_view.getPayType()) {
            WXPayUtil.pay(rechargeResData.rspBody);
        }

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(LoginEvent accountEvent) {
//        finish();
//    }
}