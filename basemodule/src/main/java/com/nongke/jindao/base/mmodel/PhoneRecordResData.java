package com.nongke.jindao.base.mmodel;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class PhoneRecordResData extends BaseResData {

    public List<PhoneRecordBody> rspBody;

    public class PhoneRecordBody {
        public String phone, status, totalMoney, discountMoney,createTime;

        @Override
        public String toString() {
            return "BillBody{" +
                    "phone='" + phone + '\'' +
                    ", status='" + status + '\'' +
                    ", totalMoney='" + totalMoney + '\'' +
                    ", discountMoney='" + discountMoney + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }
    }


}
