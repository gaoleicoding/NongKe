package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.WithdrawRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.mcontract.WithdrawRecordContract;

import io.reactivex.Observable;


public class WithdrawRecordPresenter extends BasePresenter<WithdrawRecordContract.View> implements WithdrawRecordContract.Presenter {

    @Override
    public void listUserCash() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserCash();
        addSubscribe(observable, new BaseObserver<WithdrawRecordResData>(false) {
            @Override
            public void onNext(WithdrawRecordResData withdrawRecordResData) {
                if ("10000".equals(withdrawRecordResData.retCode))
                mView.showUserCash(withdrawRecordResData);
                else ResponseStatusUtil.handleResponseStatus(withdrawRecordResData);
            }
        });
    }


}
