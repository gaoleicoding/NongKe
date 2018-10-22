package com.nongke.jindao.base.utils;

import com.gaolei.basemodule.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.BaseResData;

public class ResponseStatusUtils {

    public static void handleResponseStatus(BaseResData baseResData) {
        if ("10000".equals(baseResData.retCode)) {
            Utils.showToast(CustomApplication.context.getString(R.string.get_data_success), true);
        } else if (baseResData != null && "20000".equals(baseResData.retCode))
            Utils.showToast(baseResData.retDesc, true);
        else Utils.showToast(CustomApplication.context.getString(R.string.get_data_failure), true);
    }
}
