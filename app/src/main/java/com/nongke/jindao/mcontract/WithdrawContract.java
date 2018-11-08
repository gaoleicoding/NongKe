package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MyProfileResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class WithdrawContract {

    public interface Presenter {

        void saveUserCash(float amount, String bankName, String bankNum, String bankAddress, String userName, String phone);

        void commissionToMoney(double amount);

        void getUserProfile();
    }

    public interface View {

        void showUserProfileResData(MyProfileResData userProfileResData);

    }
}
