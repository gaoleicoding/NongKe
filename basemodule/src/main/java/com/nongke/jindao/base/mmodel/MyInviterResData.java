package com.nongke.jindao.base.mmodel;

import java.util.List;


public class MyInviterResData extends BaseResData {

    public List<InviterBody> rspBody;

    public class InviterBody {
        public String isVip, phone, uid, img, createTime;
        public int commission, id;
    }

}
