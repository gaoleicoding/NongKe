package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MyProfileResData;
import com.nongke.jindao.base.mmodel.WithdrawRecordResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class WithdrawRecordContract {

    public interface Presenter {

        void listUserCash();

    }

    public interface View {

        void showUserCash(WithdrawRecordResData withdrawRecordResData);

    }
}
