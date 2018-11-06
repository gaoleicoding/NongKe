package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.PhoneRecordResData;
import com.nongke.jindao.base.mmodel.RechargeResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class RechargeRecordContract {

    public interface Presenter {

        void listUserPhoneRecord();

    }

    public interface View {

        void showRechargeRecordRes(PhoneRecordResData phoneRecordResData);

    }
}
