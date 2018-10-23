package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.MyAddressResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class MyAddressContract {

    public interface Presenter {

        void saveOrUpdateUserAddress(String userName, String phone, String address);

        void getUserAddress();

    }

    public interface View {

        void showUserAddressResData(MyAddressResData userAddressResData);

    }
}
