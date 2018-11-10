package com.nongke.jindao.base.utils;

import com.gaolei.basemodule.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.event.LoginEvent;
import com.nongke.jindao.base.mmodel.BaseResData;

import org.greenrobot.eventbus.EventBus;

public class ResponseStatusUtil {

    public static void handleResponseStatus(BaseResData baseResData) {
        if (baseResData == null) return;
        if ("20000".equals(baseResData.retCode)) {
            if (baseResData.retDesc.contains("isv.BUSINESS_LIMIT_CONTROL"))
                Utils.showToast(CustomApplication.context.getString(R.string.get_msgcode_frequent), true);
            else
                Utils.showToast(baseResData.retDesc, true);

        } else if ("30000".equals(baseResData.retCode)) {
            EventBus.getDefault().post(new LoginEvent());
        } else Utils.showToast(baseResData.retDesc, true);
    }
}