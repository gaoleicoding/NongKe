package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MessageResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class MessageContract {

    public interface Presenter {


        void listMessage();

    }

    public interface View {

        void showMessageList(MessageResData messageResData);

    }
}
