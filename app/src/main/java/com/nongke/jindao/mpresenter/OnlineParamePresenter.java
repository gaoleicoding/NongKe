package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.OnlineParamResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.OnlineParamUtil;
import com.nongke.jindao.mcontract.MyInviterContract;
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

                OnlineParamUtil.setParamResData(paramResData);
            }
        });
    }

    @Override
    public void listMessage() {
//        HashMap hashMap = new HashMap();
//        hashMap.put("uid", UserUtil.getUserInfo().rspBody.uid);
//
//        String jsonString = new JSONObject(hashMap).toString();
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listMessage();
        addSubscribe(observable, new BaseObserver<MessageResData>(false) {
            @Override
            public void onNext(MessageResData messageResData) {
                LogUtil.d2("listMessage------------ :" + messageResData.toString());
                mView.showMessageList(messageResData);
                OnlineParamUtil.setMessageResData(messageResData);
            }
        });
    }
}
