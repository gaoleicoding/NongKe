package com.nongke.jindao.mpresenter;


import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.event.UpdateUserInfoEvent;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.LogoutResData;
import com.nongke.jindao.base.mmodel.MyProfileResData;
import com.nongke.jindao.base.mmodel.UserRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.UserInfoContract;
import com.nongke.jindao.mcontract.WithdrawContract;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class WithdrawPresenter extends BasePresenter<WithdrawContract.View> implements WithdrawContract.Presenter {
    @Override
    public void saveUserCash(float amount, String bankName, String bankNum, String bankAddress, String userName, String phone) {
        HashMap hashMap = new HashMap();
        hashMap.put("amount", amount);
        hashMap.put("bankName", bankName);
        hashMap.put("bankNum", bankNum);
        hashMap.put("bankAddress", bankAddress);
        hashMap.put("userName", userName);
        hashMap.put("phone", phone);
        String jsonString = new JSONObject(hashMap).toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).saveUserCash(body);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                if("10000".equals(baseResData.retCode)) {
                    UpdateUserInfoEvent userInfoEvent=new UpdateUserInfoEvent();
                    EventBus.getDefault().post(userInfoEvent);
                }else
                ResponseStatusUtil.handleResponseStatus(baseResData);
            }
        });
    }

    @Override
    public void getUserProfile() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserBank();
        addSubscribe(observable, new BaseObserver<MyProfileResData>(false) {
            @Override
            public void onNext(MyProfileResData userProfileResData) {
                LogUtil.d2("userProfileResData------------ :" + userProfileResData.toString());
                if ("10000".equals(userProfileResData.retCode))
                mView.showUserProfileResData(userProfileResData);
                else ResponseStatusUtil.handleResponseStatus(userProfileResData);

            }
        });
    }

    @Override
    public void commissionToMoney(double amount) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).commissionToMoney(amount);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                ResponseStatusUtil.handleResponseStatus(baseResData);
                if("10000".equals(baseResData.retCode)) {
                    UpdateUserInfoEvent userInfoEvent=new UpdateUserInfoEvent();
                    EventBus.getDefault().post(userInfoEvent);
                    mView.showCommissionToMoney(baseResData);
                    ResponseStatusUtil.handleResponseStatus(baseResData);
                }
            }
        });
    }


}
