package com.nongke.jindao.base.utils;

import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.OnlineParamResData;

public class OnlineParamUtil {

    public static OnlineParamResData paramResData;
    public static MessageResData messageResData;

    public static OnlineParamResData getParamResData() {
        return paramResData;
    }

    public static void setParamResData(OnlineParamResData paramResData) {
        OnlineParamUtil.paramResData = paramResData;
    }

    public static MessageResData getMessageResData() {
        return messageResData;
    }

    public static void setMessageResData(MessageResData messageResData) {
        OnlineParamUtil.messageResData = messageResData;
    }
}
