package com.nongke.jindao.base.utils;

import com.gaolei.basemodule.R;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.BaseResData;

public class ResponseStatusUtil {

    public static void handleResponseStatus(BaseResData baseResData) {
            Utils.showToast(baseResData.retDesc, true);

    }
}
