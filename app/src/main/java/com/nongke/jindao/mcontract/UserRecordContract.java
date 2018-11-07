package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.UserRecordResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class UserRecordContract {

    public interface Presenter {

        void listUserRecord(String type);

    }

    public interface View {

        void showUserRecord(UserRecordResData userRecordResData);

    }
}
