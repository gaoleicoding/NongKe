package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mmodel.ProductResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class ProductContract {

    public interface Presenter {


        void pageProduct(int pageSize, String orderType, String orderBy);

        void getBannerProduct();

        void onLoadMore(int pageSize, String orderType, String orderBy);
        void listMessage();
    }

    public interface View {

        void showProduct(ProductResData productResData, boolean isRefresh);
        void showBannerList(BannerResData productResData);
        void showMessageList(MessageResData messageResData);

    }
}
