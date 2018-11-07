package com.nongke.jindao.base.mmodel;

import java.util.List;

public class OrderRecordResData {
//
//    {
//        "extra": {},
//        "retCode": "string",
//            "retDesc": "string",
//            "rspBody": {
//        "endRow": 0,
//                "firstPage": 0,
//                "hasNextPage": true,
//                "hasPreviousPage": true,
//                "isFirstPage": true,
//                "isLastPage": true,
//                "lastPage": 0,
//                "list": [
//        {
//            "address": "string",
//                "cornMoney": 0,
//                "createTime": "2018-11-06T01:41:05.252Z",
//                "description": "string",
//                "discount": 0,
//                "discountMoney": 0,
//                "orderId": "string",
//                "payTime": "2018-11-06T01:41:05.252Z",
//                "payType": 0,
//                "phone": "string",
//                "postCode": "string",
//                "postage": 0,
//                "products": [
//            {
//                "amount": 0,
//                    "img": "string",
//                    "productId": 0,
//                    "productName": "string",
//                    "productPrice": 0,
//                    "total": 0
//            }
//        ],
//            "rmb": 0,
//                "statusDesc": 0,
//                "totalMoney": 0,
//                "totalPay": 0,
//                "uid": "string",
//                "userName": "string"
//        }
//    ],
//        "navigateFirstPage": 0,
//                "navigateLastPage": 0,
//                "navigatePages": 0,
//                "navigatepageNums": [
//        0
//    ],
//        "nextPage": 0,
//                "pageNum": 0,
//                "pageSize": 0,
//                "pages": 0,
//                "prePage": 0,
//                "size": 0,
//                "startRow": 0,
//                "total": 0
//    },
//        "timestamp": "string"
//    }


    public RspBody rspBody;

    public class RspBody {
        public int id, firstPage, lastPage;
        public boolean hasNextPage, hasPreviousPage, isFirstPage, isLastPage;
        public String orderId, orderType, productDetail, uid;
        public float discount, discountMoney, postage, totalCardMoney, totalCardPay, totalMoney, totalPay;
        public List<ProductOrder> list;

        @Override
        public String toString() {
            return "RspBody{" +
                    "id=" + id +
                    ", firstPage=" + firstPage +
                    ", lastPage=" + lastPage +
                    ", hasNextPage=" + hasNextPage +
                    ", hasPreviousPage=" + hasPreviousPage +
                    ", isFirstPage=" + isFirstPage +
                    ", isLastPage=" + isLastPage +
                    ", orderId='" + orderId + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", productDetail='" + productDetail + '\'' +
                    ", uid='" + uid + '\'' +
                    ", discount=" + discount +
                    ", discountMoney=" + discountMoney +
                    ", postage=" + postage +
                    ", totalCardMoney=" + totalCardMoney +
                    ", totalCardPay=" + totalCardPay +
                    ", totalMoney=" + totalMoney +
                    ", totalPay=" + totalPay +
                    ", list=" + list +
                    '}';
        }
    }
}
