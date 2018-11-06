package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.MyInviterContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MyInviterPresenter extends BasePresenter<MyInviterContract.View> implements MyInviterContract.Presenter {

    @Override
    public void listUserInviter(String uid) {
        HashMap hashMap = new HashMap();
        hashMap.put("uid", uid);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserInviter(requestBody);
        addSubscribe(observable, new BaseObserver<MyInviterResData>(false) {
            @Override
            public void onNext(MyInviterResData userInviterResData) {
                LogUtil.d2("userInviterResData------------ :" + userInviterResData.toString());

                mView.showUListUserInviter(userInviterResData);

            }
        });
    }

}
