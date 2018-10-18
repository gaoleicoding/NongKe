package com.nongke.jindao.base.thirdframe.retrofit.interceptor;

import com.nongke.jindao.base.utils.UserUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("token", UserUtils.getUserInfo().rspBody.token)
//                .addHeader("header2", "123456")
//                .addHeader("header3", "123456")
//                .addHeader("header4", "123456")
                .build();
        return chain.proceed(request);

    }
}