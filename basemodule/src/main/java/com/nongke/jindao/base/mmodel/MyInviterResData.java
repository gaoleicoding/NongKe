package com.nongke.jindao.base.mmodel;

import java.util.List;


public class MyInviterResData extends BaseResData {

    public List<InviterBody> rspBody;

    public class InviterBody {
        public String isVip, phone, uid,invitedUid, img;
        public long createTime;
        public int  id;
        public float commission;
    }

}
