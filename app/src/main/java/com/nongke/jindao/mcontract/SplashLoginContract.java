package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class SplashLoginContract {

    public interface Presenter {


        void getLoginData(String phone, String password);


    }

    public interface View {

        void showLoginResData(LoginResData loginResData);

    }

}
