package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.OnlineParamResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class OnlineParameContract {

    public interface Presenter {


        void getOnlineParame();

    }

    public interface View {

        void showOnlineParame(OnlineParamResData onlineParamResData);

    }
}
