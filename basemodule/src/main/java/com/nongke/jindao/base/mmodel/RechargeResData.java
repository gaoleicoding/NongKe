package com.nongke.jindao.base.mmodel;

import java.util.List;

public class RechargeResData extends BaseResData {

//    {
//        "extra": {},
//        "retCode": "string",
//            "retDesc": "string",
//            "rspBody": {
//        "body": "string",
//                "flag": 0,
//                "orderId": "string",
//                "orderType": 0,
//                "paySign": "string",
//                "payType": 0,
//                "subject": "string",
//                "totalMoney": 0,
//                "totalPay": 0
//    },
//        "timestamp": "string"
//    }

    public RspBody rspBody;

    public class RspBody {
        public int orderType, flag;
        public String orderId, paySign, payType, subject, body, appId, partnerId, prepayId, nonceStr, timeStamp, packageValue;
        public float totalMoney, totalPay;

        @Override
        public String toString() {
            return "RspBody{" +
                    "orderType=" + orderType +
                    ", flag=" + flag +
                    ", orderId='" + orderId + '\'' +
                    ", paySign='" + paySign + '\'' +
                    ", payType='" + payType + '\'' +
                    ", subject='" + subject + '\'' +
                    ", body='" + body + '\'' +
                    ", appId='" + appId + '\'' +
                    ", partnerId='" + partnerId + '\'' +
                    ", prepayId='" + prepayId + '\'' +
                    ", nonceStr='" + nonceStr + '\'' +
                    ", timeStamp='" + timeStamp + '\'' +
                    ", packageValue='" + packageValue + '\'' +
                    ", totalMoney=" + totalMoney +
                    ", totalPay=" + totalPay +
                    '}';
        }
    }
}
