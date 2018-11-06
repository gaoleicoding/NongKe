package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.PhoneRecordResData;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RechargeContract;
import com.nongke.jindao.mcontract.RechargeRecordContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RechargeRecordPresenter extends BasePresenter<RechargeRecordContract.View> implements RechargeRecordContract.Presenter {



    @Override
    public void listUserPhoneRecord() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listUserPhoneRecord();
        addSubscribe(observable, new BaseObserver<PhoneRecordResData>(false) {
            @Override
            public void onNext(PhoneRecordResData recordResData) {
                LogUtil.d2("listUserPhoneRecord------------ :" + recordResData.rspBody.toString());

                mView.showRechargeRecordRes(recordResData);

            }
        });
    }
}

