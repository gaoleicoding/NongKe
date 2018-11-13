package com.nongke.jindao.base.pay.wxpay;


import android.widget.Toast;

import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.Random;

/**
 * Created by xmg on 2016/12/5.
 */

public class WXPayUtil {

    private static IWXAPI iwxapi;
    public static String appId = "wx6d986f3afec37116";


    public static IWXAPI getWXAPI(String appId) {
        if (iwxapi == null) {
            //通过WXAPIFactory创建IWAPI实例
            iwxapi = WXAPIFactory.createWXAPI(CustomApplication.context, null);

            //将应用的appid注册到微信
            iwxapi.registerApp(appId);
        }
        return iwxapi;
    }

    //生成随机字符串
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //获得时间戳
    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static void pay(RechargeResData.RspBody rspBdy) {
        if (judgeCanGo()) {
            LogUtil.d("WXPayEntryActivity", "rspBdy.appId = " + rspBdy.appId);
            LogUtil.d("WXPayEntryActivity", "rspBdy.nonceStr = " + rspBdy.nonceStr);
            LogUtil.d("WXPayEntryActivity", "rspBdy.packageValue = " + rspBdy.packageValue);
            LogUtil.d("WXPayEntryActivity", "rspBdy.partnerId = " + rspBdy.partnerId);
            LogUtil.d("WXPayEntryActivity", "rspBdy.prepayId = " + rspBdy.prepayId);
            LogUtil.d("WXPayEntryActivity", "rspBdy.timeStamp = " + rspBdy.timeStamp);
            LogUtil.d("WXPayEntryActivity", "rspBdy.paySign = " + rspBdy.paySign);
            PayReq req = new PayReq();
            req.appId			= rspBdy.appId;
            req.partnerId		= rspBdy.partnerId;
            req.prepayId		= rspBdy.prepayId;
            req.nonceStr		= rspBdy.nonceStr;
            req.timeStamp		= rspBdy.timeStamp;
            req.packageValue	= rspBdy.packageValue;
            req.sign			= rspBdy.paySign;

//            req.appId = rspBdy.appId;
//            req.nonceStr = rspBdy.nonceStr;
//            req.packageValue = rspBdy.packageValue;
//            req.partnerId = rspBdy.partnerId;
//            req.prepayId = rspBdy.prepayId;
//            req.timeStamp = rspBdy.timeStamp;
//            req.sign = rspBdy.paySign;

            iwxapi.sendReq(req);
        }
    }

    private static boolean judgeCanGo() {
        getWXAPI(appId);
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(CustomApplication.context, "请先安装微信应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
