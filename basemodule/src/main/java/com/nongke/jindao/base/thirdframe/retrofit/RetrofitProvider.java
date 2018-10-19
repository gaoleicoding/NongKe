package com.nongke.jindao.base.thirdframe.retrofit;


import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.thirdframe.retrofit.UrlConfig;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.DefaultHeaderInterceptor;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.GzipRequestInterceptor;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.HttpLoggingInterceptor;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.OfflineCacheInterceptor;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.OnlineCacheInterceptor;
import com.nongke.jindao.base.thirdframe.retrofit.interceptor.RetryIntercepter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public final class RetrofitProvider {

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private static volatile RetrofitProvider sInstance;
    public static String netCachePath;

    private RetrofitProvider() {
        init();
    }


    private void  init() {
        netCachePath= CustomApplication.context.getExternalFilesDir("net_cache").getAbsolutePath();
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor())
//                    .addNetworkInterceptor(new OnlineCacheInterceptor())//有网缓存拦截器
//                    .addInterceptor(new OfflineCacheInterceptor())//无网缓存拦截器
//                    .cache(new Cache(new File(netCachePath), 50 * 10240 * 1024))//缓存路径和空间设置
//                    .addInterceptor(new RetryIntercepter(4))//重试
//                    .addInterceptor(new GzipRequestInterceptor())//开启Gzip压缩
                    .addInterceptor(new DefaultHeaderInterceptor())//请求连接中添加头信息
//                    .addInterceptor(new ProgressInterceptor())//请求url的进度
//                    .addInterceptor(new TokenInterceptor())//token过期，自动刷新Token
//                    .addInterceptor(new SignInterceptor())//所有的接口，默认需要带上sign,timestamp2个参数
//                    .addNetworkInterceptor(new ParamsEncryptInterceptor())//参数加密,一般针对表单中的字段和值进行加密，防止中途第三方进行窥探和篡改
//.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(CustomApplication.context)))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(UrlConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public static RetrofitProvider getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitProvider.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitProvider();
                }
            }
        }
        return sInstance;
    }

    public <T> T createService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}