package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class RegisterLoginContract {

    public interface Presenter {

        void getRegisterData(String phone, String password, String confirmPassword, String code);

        void getResetPasswordData(String phone, String password, String confirmPassword, String code);

        void getLoginData(String phone, String password);

        void getLogoutData();

        void getMessageCode(String phone, int type);
        void uploadImg();

    }

    public interface View {

        void showRegisterResData(RegisterResData registerResData);

        void showResetPasswordData(RegisterResData registerResData);

        void showLoginResData(LoginResData loginResData);


        void showMsgCodeResData(MsgCodeResData msgCodeResData);
    }
}
