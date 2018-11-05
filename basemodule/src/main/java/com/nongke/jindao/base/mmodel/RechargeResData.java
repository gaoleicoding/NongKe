package com.nongke.jindao.base.mmodel;

import java.util.List;

public class RechargeResData extends BaseResData{

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
        public int orderType,flag;
        public String orderId,paySign,payType,subject,body;
        public float totalMoney, totalPay;

        @Override
        public String toString() {
            return "RspBody{" +
                    "orderType=" + orderType +
                    ", paySign=" + paySign +
                    ", subject=" + subject +
                    ", flag=" + flag +
                    ", orderId='" + orderId + '\'' +
                    ", payType='" + payType + '\'' +
                    ", body='" + body + '\'' +
                    ", totalMoney=" + totalMoney +
                    ", totalPay=" + totalPay +
                    '}';
        }
    }
}
