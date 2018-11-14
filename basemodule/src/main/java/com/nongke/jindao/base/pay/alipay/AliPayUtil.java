package com.nongke.jindao.base.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.nongke.jindao.base.event.FinishOrderActivityEvent;
import com.nongke.jindao.base.event.UpdateCartEvent;
import com.nongke.jindao.base.event.UpdateUserInfoEvent;
import com.nongke.jindao.base.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class AliPayUtil {
    public static final int SDK_PAY_FLAG = 0;

    public static Handler alipayHandler = new Handler() {
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
                        Utils.showToast("支付成功", false);
                        EventBus.getDefault().post(new FinishOrderActivityEvent());
                        EventBus.getDefault().post(new UpdateCartEvent());
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Utils.showToast("支付失败", false);
                    }
                    break;

            }
        }

        ;
    };


    public static void pay(final Activity activity, final String paySign) {

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
                alipayHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
