package com.nongke.jindao.mpresenter;


import android.util.Log;

import com.google.gson.Gson;
import com.nongke.jindao.base.mmodel.ArticleListData;
import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeRequestData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.HomeContract;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;

import io.reactivex.Observable;
import okhttp3.RequestBody;


public class RegisterLoginPresenter extends BasePresenter<RegisterLoginContract.View> implements RegisterLoginContract.Presenter {


    @Override
    public void getRegisterData(String phone, String password, String confirmPassword, String code) {
        Observable observable = mRestService.getRegisterData(phone, password, confirmPassword, code);
        addSubscribe(observable, new BaseObserver<RegisterResData>(false) {
            @Override
            public void onNext(RegisterResData registerResData) {
                mView.showRegisterResData(registerResData);
            }
        });
    }

    @Override
    public void getLoginData(String phone, String password) {
        Observable observable = mRestService.getLoginData(phone, password);
        addSubscribe(observable, new BaseObserver<LoginResData>(false) {
            @Override
            public void onNext(LoginResData loginResData) {
                mView.showLoginResData(loginResData);
            }
        });
    }

    @Override
    public void getMessageCode(String phone, int type) {
        MsgCodeRequestData info=new MsgCodeRequestData(phone,type);
        Gson gson=new Gson();
        String toJson=gson.toJson(info);
        LogUtil.d2("toJson------------"+toJson);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),toJson);

        Observable observable = mRestService.getMessageCode(body);
        addSubscribe(observable, new BaseObserver<MsgCodeResData>(false) {
            @Override
            public void onNext(MsgCodeResData msgCodeResData) {
                mView.showMsgCodeResData(msgCodeResData);
            }
        });

    }
}
