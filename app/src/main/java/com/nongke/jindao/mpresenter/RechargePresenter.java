package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RechargeContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RechargePresenter extends BasePresenter<RechargeContract.View> implements RechargeContract.Presenter {


    @Override
    public void recharge(int orderType, int payType, float totalMoney, float totalPay) {
        HashMap hashMap = new HashMap();
        hashMap.put("orderType", orderType);
        hashMap.put("payType", payType);
        hashMap.put("totalMoney", totalMoney);
        hashMap.put("totalPay", totalPay);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).recharge(requestBody);
        addSubscribe(observable, new BaseObserver<RechargeResData>(false) {
            @Override
            public void onNext(RechargeResData rechargeResData) {
                if ("10000".equals(rechargeResData.retCode)) {
                    LogUtil.d2("recharge------------:" + rechargeResData.rspBody.toString());
                    mView.showRechargeRes(rechargeResData);
                } else {
                    ResponseStatusUtil.handleResponseStatus(rechargeResData);
                }

            }
        });
    }

    @Override
    public void getUserInfo() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserInfo();
        addSubscribe(observable, new BaseObserver<LoginResData>(false) {
            @Override
            public void onNext(LoginResData loginResData) {
                LogUtil.d2("getBannerProduct------------ :" + loginResData.toString());
                if ("10000".equals(loginResData.retCode))
                    mView.showUserInfo(loginResData);
                else ResponseStatusUtil.handleResponseStatus(loginResData);
            }
        });
    }
}

