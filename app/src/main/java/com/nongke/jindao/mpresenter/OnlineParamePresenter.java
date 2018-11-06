package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.OnlineParamResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.mcontract.OnlineParameContract;

import io.reactivex.Observable;


public class OnlineParamePresenter extends BasePresenter<OnlineParameContract.View> implements OnlineParameContract.Presenter {

    @Override
    public void getOnlineParame() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getOnlineParame();
        addSubscribe(observable, new BaseObserver<OnlineParamResData>(false) {
            @Override
            public void onNext(OnlineParamResData paramResData) {
                LogUtil.d2("OnlineParamePresenter------------ :" + paramResData.rspBody.vip_phone_discount.toString());
                mView.showOnlineParame(paramResData);
                OnlineParamUtil.setParamResData(paramResData);
            }
        });
    }


}
