package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.mmodel.ArticleListData;
import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.mcontract.HomeContract;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.thirdframe.rxjava.BaseObserver;

import io.reactivex.Observable;


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
        Observable observable = mRestService.getMessageCode(phone, type);
        addSubscribe(observable, new BaseObserver<MsgCodeResData>(false) {
            @Override
            public void onNext(MsgCodeResData msgCodeResData) {
                mView.showMsgCodeResData(msgCodeResData);
            }
        });

    }
}
