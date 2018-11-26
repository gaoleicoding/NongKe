package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.event.LoginEvent;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.pay.wxpay.WXPayUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.mcontract.RechargeContract;
import com.nongke.jindao.mpresenter.RechargePresenter;
import com.nongke.jindao.view.PayView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class DaoliRechargeActivity extends BaseMvpActivity<RechargePresenter> implements RechargeContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_daoli_desc)
    TextView tv_daoli_desc;
    @BindView(R.id.tv_daoli_recharge)
    TextView tv_daoli_recharge;
    @BindView(R.id.tv_daoli_amount)
    TextView tv_daoli_amount;
    @BindView(R.id.et_recharge_amount)
    EditText et_recharge_amount;
    @BindView(R.id.ll_pay_view)
    PayView pay_view;
    String daoli_recharge_limit_100;
    float rechargeAmount;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DaoliRechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daoli_recharge;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_daoli_recharge));
        iv_back.setVisibility(View.VISIBLE);
        if (OnlineParamUtil.paramResData == null || OnlineParamUtil.paramResData.rspBody == null)
            return;
        daoli_recharge_limit_100 = OnlineParamUtil.getParamResData().rspBody.daoli_recharge_limit_100.content;
        if ("true".equals(daoli_recharge_limit_100)) {
            et_recharge_amount.setHint(getResources().getString(R.string.input_with_amount_hint));
        }
        tv_daoli_desc.setText(OnlineParamUtil.paramResData.rspBody.daoli_use_desc.content);
        tv_daoli_amount.setText(UserUtil.getUserInfo().rspBody.cardMoney + "");
        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.tv_daoli_recharge})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_daoli_recharge:
                String ammountStr = et_recharge_amount.getText().toString();
                rechargeAmount = Utils.stringToInt(ammountStr);
                if ("true".equals(daoli_recharge_limit_100)) {
                    if (rechargeAmount % 100 != 0) {
                        Utils.showToast("请输入整百金额", true);
                        return;
                    }
                }
                mPresenter.recharge(2, pay_view.getPayType(), rechargeAmount, rechargeAmount);

                break;

        }

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
        if (3 == pay_view.getPayType())
            AliPayUtil.pay(this, paySign);
        if (4 == pay_view.getPayType()) {
            WXPayUtil.pay(rechargeResData.rspBody);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent accountEvent) {
        tv_daoli_amount.setText(UserUtil.getUserInfo().rspBody.cardMoney + "");
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}