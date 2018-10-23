package com.nongke.jindao.base.mmodel;

import java.util.List;

public class MyInviterResData extends BaseResData {

    private List<Body> rspBody;

    public class Body {
        public String isVip, phone, uid, img;
        public int commission, id;
    }

}
