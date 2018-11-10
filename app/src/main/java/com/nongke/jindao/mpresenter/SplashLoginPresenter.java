package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.mcontract.SplashLoginContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SplashLoginPresenter extends BasePresenter<SplashLoginContract.View> implements SplashLoginContract.Presenter {



    @Override
    public void getLoginData(String phone, String password) {
        HashMap hashMap = new HashMap();
        hashMap.put("phone", phone);
        hashMap.put("password", password);
        String jsonString = new JSONObject(hashMap).toString();
        LogUtil.d2("jsonString------------" + jsonString);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getLoginData(body);
        addSubscribe(observable, new BaseObserver<LoginResData>(false) {
            @Override
            public void onNext(LoginResData loginResData) {
                mView.showLoginResData(loginResData);
            }
        });
    }
}
