package com.nongke.jindao.base.thirdframe.retrofit.interceptor;

import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.UserUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        LoginResData  loginResData= UserUtil.getUserInfo();
        if(loginResData!=null)
        LogUtil.d("token-------------"+ UserUtil.getUserInfo().rspBody.token);
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("token", loginResData==null?"":loginResData.rspBody.token)
//                .addHeader("header2", "123456")
//                .addHeader("header3", "123456")
//                .addHeader("header4", "123456")
                .build();
        return chain.proceed(request);

    }
}