package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.PhoneRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.mcontract.MyBillContract;
import com.nongke.jindao.mcontract.RechargeRecordContract;

import io.reactivex.Observable;


public class MyBillPresenter extends BasePresenter<MyBillContract.View> implements MyBillContract.Presenter {



    @Override
    public void listUserBill() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserBill();
        addSubscribe(observable, new BaseObserver<BillResData>(false) {
            @Override
            public void onNext(BillResData billResData) {
                if ("10000".equals(billResData.retCode))
                mView.showUserBill(billResData);
                else ResponseStatusUtil.handleResponseStatus(billResData);
            }
        });
    }
}

