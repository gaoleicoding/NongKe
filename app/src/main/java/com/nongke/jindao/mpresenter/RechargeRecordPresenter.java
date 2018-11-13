package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.PhoneRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.mcontract.RechargeRecordContract;

import io.reactivex.Observable;


public class RechargeRecordPresenter extends BasePresenter<RechargeRecordContract.View> implements RechargeRecordContract.Presenter {



    @Override
    public void listUserPhoneRecord() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserPhoneRecord();
        addSubscribe(observable, new BaseObserver<PhoneRecordResData>(false) {
            @Override
            public void onNext(PhoneRecordResData recordResData) {
                if ("10000".equals(recordResData.retCode))
                mView.showRechargeRecordRes(recordResData);
                else ResponseStatusUtil.handleResponseStatus(recordResData);
            }
        });
    }
}

