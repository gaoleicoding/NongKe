package com.nongke.jindao.base.mmodel;

public class MyAddressResData extends BaseResData{

    public RspBody rspBody;

    public class RspBody {
        public String userName, phone, address, uid;

        @Override
        public String toString() {
            return "RspBody{" +
                    "userName='" + userName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", uid='" + uid + '\'' +
                    '}';
        }
    }

}
