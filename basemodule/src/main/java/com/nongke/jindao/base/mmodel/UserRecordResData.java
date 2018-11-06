package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class UserRecordResData extends BaseResData {

    public List<UserRecordBody> rspBody;

    public class UserRecordBody {
        public float amount;
        public int payType,type;
        public String uid, description, createTime;

    }


}
