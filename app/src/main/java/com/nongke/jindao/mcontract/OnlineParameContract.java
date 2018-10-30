package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.OnlineParamResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class OnlineParameContract {

    public interface Presenter {


        void getOnlineParame();

        void listMessage();

    }

    public interface View {

        void showOnlineParame(OnlineParamResData onlineParamResData);

        void showMessageList(MessageResData messageResData);
    }
}
