package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.PhoneRecordResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class MyBillContract {

    public interface Presenter {

        void listUserBill();

    }

    public interface View {

        void showUserBill(BillResData billResData);

    }
}
