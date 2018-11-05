package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;

import java.util.List;

/**
 * Created by gaolei on 2018/6/18.
 */

public class OrderProductContract {

    public interface Presenter {

        void buyProduct(List<Product> list);

        void getUserAddress();

    }

    public interface View {

        void showOrderProduct(OrderProductResData productResData);

        void showUserAddressResData(MyAddressResData userAddressResData);

    }
}
