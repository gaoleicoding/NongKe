package com.nongke.jindao.base.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class AliPayUtil {
    public static final int SDK_PAY_FLAG = 0;

    public static void pay(final Handler mHandler, final Activity activity, final String paySign) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(paySign, true);
                Message msg = new Message();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
