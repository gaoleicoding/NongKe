package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BillResData;
import com.nongke.jindao.base.mmodel.UserRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.MyBillContract;
import com.nongke.jindao.mcontract.UserRecordContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class UserRecordPresenter extends BasePresenter<UserRecordContract.View> implements UserRecordContract.Presenter {



    @Override
    public void listUserRecord(int type) {

        HashMap hashMap = new HashMap();
        hashMap.put("type", type);
        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserRecord(requestBody);
        addSubscribe(observable, new BaseObserver<UserRecordResData>(false) {
            @Override
            public void onNext(UserRecordResData userRecordResData) {
                LogUtil.d2("listUserRecord------------ :" + userRecordResData.rspBody.toString());

                mView.showUserRecord(userRecordResData);

            }
        });
    }
}

