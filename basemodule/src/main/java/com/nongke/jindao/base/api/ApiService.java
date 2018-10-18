package com.nongke.jindao.base.api;


import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.LogoutResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {


    //登录
    @POST("user/login")
    Observable<LoginResData> getLoginData(@Body RequestBody requestBody);
    //登出
    @POST("user/loginOut")
    Observable<LogoutResData> getLogoutData(@Body RequestBody requestBody);

    //注册
    @POST("user/register")
    Observable<RegisterResData> getRegisterData(@Body RequestBody requestBody); //注册

    @POST("user/resetPassword")
    Observable<RegisterResData> getResetPassword(@Body RequestBody requestBody);

    //获取验证码，type：1注册 2找回手机密码
//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/getMessageCode")
//    @FormUrlEncoded
    Observable<MsgCodeResData> getMessageCode(@Body RequestBody requestBody);

}
