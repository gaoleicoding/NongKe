package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MyInviterResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class MyInviterContract {

    public interface Presenter {


        void listUserInviter();

    }

    public interface View {

        void showUListUserInviter(MyInviterResData userContactResData);

    }
}
