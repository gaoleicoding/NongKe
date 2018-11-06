package com.nongke.jindao.base.utils.account;

import com.nongke.jindao.base.mmodel.MessageResData;
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
