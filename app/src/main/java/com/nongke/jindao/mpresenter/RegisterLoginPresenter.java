package com.nongke.jindao.mpresenter;


import com.google.gson.Gson;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeReqData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RegisterLoginPresenter extends BasePresenter<RegisterLoginContract.View> implements RegisterLoginContract.Presenter {


    @Override
    public void getRegisterData(String phone, String password, String confirmPassword, String code) {
        HashMap hashMap = new HashMap();
        hashMap.put("phone", phone);
        hashMap.put("password", password);
        hashMap.put("confirmPassword", confirmPassword);
        hashMap.put("code", code);
        String jsonString = new JSONObject(hashMap).toString();
        LogUtil.d2("jsonString------------" + jsonString);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getRegisterData(body);
        addSubscribe(observable, new BaseObserver<RegisterResData>(false) {
            @Override
            public void onNext(RegisterResData registerResData) {
                mView.showRegisterResData(registerResData);
            }
        });
    }

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

    @Override
    public void getMessageCode(String phone, int type) {
        HashMap hashMap = new HashMap();
        hashMap.put("phone", phone);
        hashMap.put("type", type);
        String jsonString = new JSONObject(hashMap).toString();
        LogUtil.d2("jsonString------------" + jsonString);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getMessageCode(body);
        addSubscribe(observable, new BaseObserver<MsgCodeResData>(false) {
            @Override
            public void onNext(MsgCodeResData msgCodeResData) {
                LogUtil.d2("getMessageCode------------msgCodeResData:" + msgCodeResData.toString());
                mView.showMsgCodeResData(msgCodeResData);
            }
        });
    }


}
