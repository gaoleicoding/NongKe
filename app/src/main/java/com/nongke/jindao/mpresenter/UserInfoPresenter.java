package com.nongke.jindao.mpresenter;


import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.LogoutResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.UserInfoContract;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {
    @Override
    public void getUserInfo() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserInfo();
        addSubscribe(observable, new BaseObserver<LoginResData>(false) {
            @Override
            public void onNext(LoginResData loginResData) {
                LogUtil.d2("getBannerProduct------------ :" + loginResData.toString());

                mView.showUserInfo(loginResData);

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


}
