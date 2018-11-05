package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.RechargeResData;

import java.util.List;

/**
 * Created by gaolei on 2018/6/18.
 */

public class RechargeContract {

    public interface Presenter {

        void recharge(int orderType,int payType,float totalMoney,float totalPay);
        void getUserInfo();

    }

    public interface View {

        void showRechargeRes(RechargeResData rechargeResData);
        void showUserInfo(LoginResData loginResData);

    }
}
