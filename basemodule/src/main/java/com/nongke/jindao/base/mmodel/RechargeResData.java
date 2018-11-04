package com.nongke.jindao.base.mmodel;

import java.util.List;

public class RechargeResData {

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
        public int orderType,paySign,subject,flag;
        public String orderId,payType,body;
        public float totalMoney, totalPay;


    }
}
