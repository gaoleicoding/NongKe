package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MyProfileResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class MyProfileContract {

    public interface Presenter {

        void saveOrUpdateUserProfile(String bankName, String bankAddress, String bankNum, String userName, String phone, String code);

        void getUserProfile();
        void getMessageCodeForUpdate();

    }

    public interface View {

        void showUserProfileResData(MyProfileResData userProfileResData);

    }
}
