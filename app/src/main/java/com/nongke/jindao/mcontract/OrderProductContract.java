package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mmodel.RechargeResData;

import java.util.List;

/**
 * Created by gaolei on 2018/6/18.
 */

public class OrderProductContract {

    public interface Presenter {

        void buyProduct(List<Product> list);

        void getUserAddress();
        void payForProductOnline(String orderId,int orderType, int payType, String list,float cornMoney,float rmb,
                                 float totalPay,float postage,String uid,String phone,String userName,String address);
    }

    public interface View {

        void showOrderProduct(OrderProductResData productResData);

        void showUserAddressResData(MyAddressResData userAddressResData);
        void showOrderProductPayRes(RechargeResData rechargeResData);
    }
}
