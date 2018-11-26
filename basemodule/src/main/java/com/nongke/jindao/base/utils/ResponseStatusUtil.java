package com.nongke.jindao.base.utils;

import com.gaolei.basemodule.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.event.FreezeEvent;
import com.nongke.jindao.base.mmodel.BaseResData;

import org.greenrobot.eventbus.EventBus;

public class ResponseStatusUtil {
    public static String STATUS_10000="10000";
    public static String STATUS_20000="20000";
    public static String STATUS_30000="30000";

    public static void handleResponseStatus(BaseResData baseResData) {
        if (baseResData == null) return;
        if ("20000".equals(baseResData.retCode)) {
            if (baseResData.retDesc.contains("isv.BUSINESS_LIMIT_CONTROL"))
                Utils.showToast(CustomApplication.context.getString(R.string.get_msgcode_frequent), true);
            else
                Utils.showToast(baseResData.retDesc, true);

        } else if ("30000".equals(baseResData.retCode)) {
            EventBus.getDefault().post(new FreezeEvent());
        } else Utils.showToast(baseResData.retDesc, true);
    }
}