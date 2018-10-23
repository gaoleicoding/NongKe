package com.nongke.jindao.mpresenter;


import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.UpdateAddressResData;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.MyAddressContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MyAddressPresenter extends BasePresenter<MyAddressContract.View> implements MyAddressContract.Presenter {


    @Override
    public void saveOrUpdateUserAddress(String userName, String phone, String address) {
        HashMap hashMap = new HashMap();
        hashMap.put("userName", userName);
        hashMap.put("phone", phone);
        hashMap.put("address", address);
        String jsonString = new JSONObject(hashMap).toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).saveOrUpdateUserAddress(body);
        addSubscribe(observable, new BaseObserver<UpdateAddressResData>(false) {
            @Override
            public void onNext(UpdateAddressResData updateAddressResData) {
                if ("10000".equals(updateAddressResData.retCode))
                    Utils.showToast(CustomApplication.context.getString(R.string.address_save_success), true);
                LogUtil.d2("updateAddressResData------------" + updateAddressResData.toString());
            }
        });
    }
    @Override
    public void getUserAddress() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserAddress();
        addSubscribe(observable, new BaseObserver<MyAddressResData>(false) {
            @Override
            public void onNext(MyAddressResData userAddressResData) {
                LogUtil.d2("userAddressResData------------ :" + userAddressResData.toString());
                mView.showUserAddressResData(userAddressResData);
//                if (logoutResData.retDesc.contains("未登录"))
//                    Utils.showToast(CustomApplication.context.getString(R.string.user_not_login), true);
//
            }
        });
    }
}
