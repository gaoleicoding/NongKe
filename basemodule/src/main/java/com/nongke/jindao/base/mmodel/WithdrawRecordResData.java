package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class WithdrawRecordResData extends BaseResData {

    public List<WithdrawRecord> rspBody;

    public class WithdrawRecord {
        public String bankName, bankNum, bankAdress, userName,phone,applyTime;
        public int amount,status;

    }


}
