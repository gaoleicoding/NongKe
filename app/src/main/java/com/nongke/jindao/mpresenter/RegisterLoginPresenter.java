package com.nongke.jindao.mpresenter;


import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.LogoutResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    public void getResetPasswordData(String phone, String password, String confirmPassword, String code) {
        HashMap hashMap = new HashMap();
        hashMap.put("phone", phone);
        hashMap.put("password", password);
        hashMap.put("confirmPassword", confirmPassword);
        hashMap.put("code", code);
        String jsonString = new JSONObject(hashMap).toString();
        LogUtil.d2("jsonString------------" + jsonString);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getResetPassword(body);
        addSubscribe(observable, new BaseObserver<RegisterResData>(false) {
            @Override
            public void onNext(RegisterResData registerResData) {
                mView.showResetPasswordData(registerResData);
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
    public void getLogoutData() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getLogoutData();
        addSubscribe(observable, new BaseObserver<LogoutResData>(false) {
            @Override
            public void onNext(LogoutResData logoutResData) {
                LogUtil.d2("logoutResData------------ :" + logoutResData.toString());
                if (logoutResData.retDesc.contains("未登录"))
                    Utils.showToast(CustomApplication.context.getString(R.string.user_not_login), true);

            }
        });
    }

    @Override
    public void uploadImg(String path) {
        final File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).uploadImg(photo);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                LogUtil.d2("uploadImg------------ :" + baseResData.toString());
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
