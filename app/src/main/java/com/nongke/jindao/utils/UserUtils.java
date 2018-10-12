package com.nongke.jindao.utils;

import android.content.Intent;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.activity.LoginRegisterActivity;
import com.nongke.jindao.base.application.CustomApplication;

public class UserUtils {

    public static boolean logined = false;

    public static boolean isLogined() {
        return logined;
    }

    public static void setLogined(boolean logined) {
        UserUtils.logined = logined;
    }

    public static void handleNotLogin() {
        CustomApplication.context.startActivity(new Intent(CustomApplication.context, LoginRegisterActivity.class));
    }

}
