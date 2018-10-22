package com.nongke.jindao.base.mmodel;

public class UserAddressResData {
    public String retCode, retDesc, timestamp;
    public RspBody rspBody;

    public class RspBody {
        public String userName, phone, address, uid;
    }

}
