package com.nongke.jindao.base.utils;

import com.nongke.jindao.base.event.LoginAccountEvent;
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
        setLogined(true);
        LoginAccountEvent accountEvent = new LoginAccountEvent();
        EventBus.getDefault().post(accountEvent);

    }
}
