package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.OrderRecordResData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class OrderRecordContract {

    public interface Presenter {

        void pageUserOrderInfo();

        void onLoadMore();

        void confirmUserOrderInfo(String orderId);

        void deleteUserOrderInfo(String orderId);
    }

    public interface View {

        void showUserOrderInfo(OrderRecordResData orderRecordResData);

    }
}
