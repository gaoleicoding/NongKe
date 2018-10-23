package com.nongke.jindao.mpresenter;


import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.UpdateAddressResData;
import com.nongke.jindao.base.mmodel.UpdateProfileResData;
import com.nongke.jindao.base.mmodel.UserAddressResData;
import com.nongke.jindao.base.mmodel.UserProfileResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.UserAddressContract;
import com.nongke.jindao.mcontract.UserProfileContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Presenter {


    @Override
    public void saveOrUpdateUserProfile(String bankName, String bankNum, String userName, String bankAddress, String phone, String code) {
        HashMap hashMap = new HashMap();
        hashMap.put("bankName", bankName);
        hashMap.put("bankAdress", bankAddress);
        hashMap.put("bankNum", bankNum);
        hashMap.put("userName", userName);
        hashMap.put("phone", phone);
        hashMap.put("code", code);
        String jsonString = new JSONObject(hashMap).toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).saveOrUpdateUserBank(body);
        addSubscribe(observable, new BaseObserver<UpdateProfileResData>(false) {
            @Override
            public void onNext(UpdateProfileResData updateProfileResData) {

                ResponseStatusUtils.handleResponseStatus(updateProfileResData);
                LogUtil.d2("updateAddressResData------------" + updateProfileResData.toString());
            }
        });
    }

    @Override
    public void getUserProfile() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserBank();
        addSubscribe(observable, new BaseObserver<UserProfileResData>(false) {
            @Override
            public void onNext(UserProfileResData userProfileResData) {
                LogUtil.d2("userProfileResData------------ :" + userProfileResData.toString());

                mView.showUserProfileResData(userProfileResData);

            }
        });
    }

    @Override
    public void getMessageCodeForUpdate() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getMessageCodeForUpdate();
        addSubscribe(observable, new BaseObserver<MsgCodeResData>(false) {
            @Override
            public void onNext(MsgCodeResData msgCodeResData) {
                LogUtil.d2("getMessageCode------------msgCodeResData:" + msgCodeResData.toString());
                if ("10000".equals(msgCodeResData.retCode)) {
                    Utils.showToast(CustomApplication.context.getString(R.string.get_msgcode_success), true);
                } else if (msgCodeResData.retDesc != null && msgCodeResData.retDesc.contains("isv.BUSINESS_LIMIT_CONTROL") && "20000".equals(msgCodeResData.retCode))
                    Utils.showToast(CustomApplication.context.getString(R.string.get_msgcode_frequent), true);
                else if (msgCodeResData.retDesc != null && "20000".equals(msgCodeResData.retCode))
                    Utils.showToast(msgCodeResData.retDesc, true);
                else
                    Utils.showToast(CustomApplication.context.getString(R.string.get_msgcode_failure), true);
            }
        });
    }
}
