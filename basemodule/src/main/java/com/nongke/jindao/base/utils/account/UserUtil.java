package com.nongke.jindao.base.utils.account;

import com.nongke.jindao.base.event.LoginEvent;
import com.nongke.jindao.base.mmodel.LoginResData;

import org.greenrobot.eventbus.EventBus;

public class UserUtil {

    public static boolean logined = false;
    public static LoginResData userInfo;

    public static boolean isLogined() {
        return logined;
    }

    public static void setLogined(boolean logined) {
        UserUtil.logined = logined;
    }

    public static LoginResData getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(LoginResData userInfo) {
        UserUtil.userInfo = userInfo;
        if (userInfo != null) {
            setLogined(true);
        } else setLogined(false);
        LoginEvent accountEvent = new LoginEvent();
        EventBus.getDefault().post(accountEvent);

    }
}
