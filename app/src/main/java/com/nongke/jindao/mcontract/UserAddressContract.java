package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mmodel.UserAddressResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class UserAddressContract {

    public interface Presenter {

        void saveOrUpdateUserAddress(String userName, String phone, String address);

        void getUserAddress();

    }

    public interface View {

        void showUserAddressResData(UserAddressResData userAddressResData);

    }
}
