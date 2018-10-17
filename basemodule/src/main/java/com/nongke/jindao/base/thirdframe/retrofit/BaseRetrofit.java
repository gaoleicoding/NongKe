package com.nongke.jindao.base.thirdframe.retrofit;

import android.util.Log;


import com.nongke.jindao.base.thirdframe.retrofit.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qqweq on 2018/2/28.
 */

public class BaseRetrofit {
    private Retrofit retrofit;
    private static final int timeout = 10000;

    private BaseRetrofit() {
        init();
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();


    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        return okHttpClient;
    }

    public <T> T createService(Class<T> tClass) {
        return retrofit.create(tClass);
    }

    private volatile static BaseRetrofit INSTANCE = null;

    //获取单例
    public static BaseRetrofit getInstance() {
        if (INSTANCE == null) {
            synchronized (BaseRetrofit.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseRetrofit();
                }
            }
        }
        return INSTANCE;
    }
}