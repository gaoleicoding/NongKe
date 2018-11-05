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

import com.alipay.sdk.app.PayTask;
import com.nongke.jindao.base.pay.PayResult;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.pay.alipay.AliPayUtil;
import com.nongke.jindao.base.utils.OnlineParamUtil;
import com.nongke.jindao.base.utils.UserUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RechargeContract;
import com.nongke.jindao.mpresenter.RechargePresenter;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class DaoliRechargeActivity extends BaseMvpActivity<RechargePresenter> implements RechargeContract.View  {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_pay_alipay)
    LinearLayout ll_pay_alipay;
    @BindView(R.id.ll_pay_wechat)
    LinearLayout ll_pay_wechat;
    @BindView(R.id.img_pay_alipay)
    ImageView img_pay_alipay;
    @BindView(R.id.img_pay_wechat)
    ImageView img_pay_wechat;
    @BindView(R.id.tv_daoli_desc)
    TextView tv_daoli_desc;
    @BindView(R.id.tv_daoli_recharge)
    TextView tv_daoli_recharge;
    @BindView(R.id.et_recharge_amount)
    EditText et_recharge_amount;

    public final int SDK_PAY_FLAG = 0, REFRESH_RECHARGE_VIP = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(DaoliRechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessageDelayed(REFRESH_RECHARGE_VIP, 1000);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(DaoliRechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case REFRESH_RECHARGE_VIP:
                    mPresenter.getUserInfo();
                    break;
            }
        }

        ;
    };
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
    }


    @OnClick({R.id.tv_daoli_recharge})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.tv_daoli_recharge:
                String ammount=et_recharge_amount.getText().toString();
                if(Utils.intPattern.matcher(ammount).matches()) {
                    mPresenter.recharge(2, 3, Utils.stringToInt(ammount), Utils.stringToInt(ammount));
                }else {
                    Utils.showToast("请输入整数金额",false);
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
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(DaoliRechargeActivity.this);
//                Map<String, String> result = alipay.payV2(paySign, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
        AliPayUtil.pay(mHandler,this,paySign);
    }

    @Override
    public void showUserInfo(LoginResData loginResData) {
        UserUtil.setUserInfo(loginResData);
    }
}