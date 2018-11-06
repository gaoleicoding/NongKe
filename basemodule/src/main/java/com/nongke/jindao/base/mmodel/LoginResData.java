package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class LoginResData extends BaseResData{

    /*{
        "extra": {},
        "retCode": "string",
            "retDesc": "string",
            "rspBody": {
        "cardMoney": 0,
                "code": "string",
                "commission": 0,
                "confirmPassword": "string",
                "createTime": "2018-10-15T10:42:55.949Z",
                "id": 0,
                "img": "string",
                "imgUrl": "string",
                "inviterPhone": "string",
                "inviterUid": "string",
                "isManager": 0,
                "isVip": 0,
                "money": 0,
                "pageNum": 0,
                "pageSize": 0,
                "password": "string",
                "phone": "string",
                "status": 0,
                "token": "string",
                "uid": "10212014"
    },
        "timestamp": "string"
    }*/


    public RspBody rspBody;

    public class RspBody {
        public String code, img, imgUrl, inviterPhone, inviterUid, phone, token, uid;
        public int  id, isManager, isVip, status;
        public float cardMoney, commission, money;

        @Override
        public String toString() {
            return "RspBody{" +
                    "code='" + code + '\'' +
                    ", img='" + img + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", inviterPhone='" + inviterPhone + '\'' +
                    ", inviterUid='" + inviterUid + '\'' +
                    ", phone='" + phone + '\'' +
                    ", token='" + token + '\'' +
                    ", uid='" + uid + '\'' +
                    ", commission=" + commission +
                    ", id=" + id +
                    ", isManager=" + isManager +
                    ", isVip=" + isVip +
                    ", money=" + money +
                    ", status=" + status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResData{" +
                "retCode='" + retCode + '\'' +
                ", retDesc='" + retDesc + '\'' + (rspBody == null ? "" :
                ", rspBody=" + rspBody.toString()) +
                '}';
    }

}
