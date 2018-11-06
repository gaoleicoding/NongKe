package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class BillResData extends BaseResData {

    public List<BillBody> rspBody;

    public class BillBody {
        public String uid, totalDesc, description, time;

    }


}
