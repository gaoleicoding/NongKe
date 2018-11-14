package com.nongke.jindao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nongke.jindao.base.event.FinishOrderActivityEvent;
import com.nongke.jindao.base.event.UpdateCartEvent;
import com.nongke.jindao.base.event.UpdateUserInfoEvent;
import com.nongke.jindao.base.pay.wxpay.WXPayUtil;
import com.nongke.jindao.base.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WXPayUtil.appId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        LogUtil.d(TAG, "WXPayEntryActivity = " + req.openId);
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d(TAG, "WXPayEntryActivity = " + resp.errCode);
        LogUtil.d(TAG, "WXPayEntryActivity = " + resp.errStr);
        LogUtil.d(TAG, "WXPayEntryActivity = " + resp.transaction);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) { //支付成功
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new FinishOrderActivityEvent());
                EventBus.getDefault().post(new UpdateCartEvent());
                EventBus.getDefault().post(new UpdateUserInfoEvent());
            } else if (resp.errCode == -2) {
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}