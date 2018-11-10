package com.nongke.jindao.base.mmodel;

import java.util.List;

public class OrderProductResData extends BaseResData{

    //    {
//        "extra": {},
//        "retCode": "string",
//            "retDesc": "string",
//            "rspBody": {
//        "createTime": "2018-11-04T00:30:38.199Z",
//                "discount": 0,
//                "discountDetail": "string",
//                "discountMoney": 0,
//                "id": 0,
//                "list": [
//        {
//            "amount": 0,
//                "cost": 0,
//                "costPrice": 0,
//                "img": "string",
//                "productId": 0,
//                "productName": "string",
//                "productPrice": 0,
//                "total": 0
//        }
//    ],
//        "orderId": "string",
//                "orderType": 0,
//                "postage": 0,
//                "productDetail": "string",
//                "totalCardMoney": 0,
//                "totalCardPay": 0,
//                "totalMoney": 0,
//                "totalPay": 0,
//                "uid": "string"
//    },
//        "timestamp": "string"
//    }
    public RspBody rspBody;

    public class RspBody {
        public int id,orderType;
        public String orderId, productDetail, uid;
        public float  discount, discountMoney,postage, totalCardMoney, totalCardPay, totalMoney, totalPay;
        public List<Product> list;

        @Override
        public String toString() {
            return "RspBody{" +
                    "discount=" + discount +
                    ", discountMoney=" + discountMoney +
                    ", id=" + id +
                    ", totalCardMoney=" + totalCardMoney +
                    ", totalCardPay=" + totalCardPay +
                    ", totalMoney=" + totalMoney +
                    ", totalPay=" + totalPay +
                    ", orderId='" + orderId + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", productDetail='" + productDetail + '\'' +
                    ", uid='" + uid + '\'' +
                    ", list=" + list +
                    '}';
        }
    }
}
