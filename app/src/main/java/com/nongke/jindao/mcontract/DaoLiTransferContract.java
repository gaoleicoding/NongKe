package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.LoginResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class DaoLiTransferContract {

    public interface Presenter {

        void cardMoneyToUser(int amount,String phone);
    }
    public interface View {

        void showCardMoneyToUser(BaseResData baseResData);
    }
}
