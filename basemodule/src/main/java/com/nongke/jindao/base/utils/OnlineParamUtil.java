package com.nongke.jindao.base.utils;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.OnlineParamResData;

public class OnlineParamUtil {

    public static OnlineParamResData paramResData;

    public static OnlineParamResData getParamResData() {
        return paramResData;
    }

    public static void setParamResData(OnlineParamResData paramResData) {
        OnlineParamUtil.paramResData = paramResData;
    }

}
