package com.nongke.jindao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nongke.jindao.base.event.LoginAccountEvent;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.pay.alipay.PayResult;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.pay.wxpay.WXPayUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RechargeContract;
import com.nongke.jindao.mpresenter.RechargePresenter;
import com.nongke.jindao.view.PayView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

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
        tv_daoli_desc.setText(OnlineParamUtil.paramResData.rspBody.daoli_use_desc.content);
        tv_daoli_amount.setText(UserUtil.getUserInfo().rspBody.cardMoney + "");
    }


    @OnClick({R.id.tv_daoli_recharge})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_daoli_recharge:
                String ammount = et_recharge_amount.getText().toString();
                if (Utils.intPattern.matcher(ammount).matches()) {
                    mPresenter.recharge(2, pay_view.getPayType(), Utils.stringToInt(ammount), Utils.stringToInt(ammount));
                } else {
                    Utils.showToast("请输入整数金额", false);
                }

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

    @Override
    public void showUserInfo(LoginResData loginResData) {
        UserUtil.setUserInfo(loginResData);
        tv_daoli_amount.setText(loginResData.rspBody.cardMoney + "");
    }

}