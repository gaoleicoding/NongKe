package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.event.UpdateUserInfoEvent;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.mcontract.DaoLiTransferContract;
import com.nongke.jindao.mcontract.MyInviterContract;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class DaoLiTransferPresenter extends BasePresenter<DaoLiTransferContract> implements DaoLiTransferContract.Presenter {

    @Override
    public void cardMoneyToUser(int amount, String phone) {
        HashMap hashMap = new HashMap();
        hashMap.put("amount", amount);
        hashMap.put("phone", phone);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).cardMoneyToUser(requestBody);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData resData) {
                ResponseStatusUtil.handleResponseStatus(resData);
               if("10000".equals(resData.retCode)) {
                   UpdateUserInfoEvent userInfoEvent=new UpdateUserInfoEvent();
                   EventBus.getDefault().post(userInfoEvent);
               }

            }
        });
    }



}
