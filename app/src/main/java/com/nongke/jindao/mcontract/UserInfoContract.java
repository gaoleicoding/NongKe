package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class UserInfoContract {

    public interface Presenter {


        void uploadImg(String path);
        void getLogoutData();
        void getUserInfo();
    }


    public interface View {
        void showUserInfo(LoginResData loginResData);
    }
}
