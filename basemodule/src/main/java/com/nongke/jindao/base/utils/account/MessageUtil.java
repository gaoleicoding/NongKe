package com.nongke.jindao.base.utils.account;

import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.OnlineParamResData;

public class MessageUtil {

    public static MessageResData messageResData;

    public static MessageResData getMessageResData() {
        return messageResData;
    }

    public static void setMessageResData(MessageResData messageResData) {
        MessageUtil.messageResData = messageResData;
    }
}
