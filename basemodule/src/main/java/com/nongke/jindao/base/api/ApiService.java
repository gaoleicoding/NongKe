package com.nongke.jindao.base.api;


import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.LogoutResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.mmodel.UpdateAddressResData;
import com.nongke.jindao.base.mmodel.UpdateProfileResData;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mmodel.MyInviterResData;
import com.nongke.jindao.base.mmodel.MyProfileResData;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiService {


    //登录
    @POST("user/login")
    Observable<LoginResData> getLoginData(@Body RequestBody requestBody);

    //登出
    @POST("user/loginOut")
    Observable<LogoutResData> getLogoutData();

    //注册
    @POST("user/register")
    Observable<RegisterResData> getRegisterData(@Body RequestBody requestBody); //注册

    @POST("user/resetPassword")
    Observable<RegisterResData> getResetPassword(@Body RequestBody requestBody);

    //获取验证码，type：1注册 2找回手机密码
//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/getMessageCode")
    Observable<MsgCodeResData> getMessageCode(@Body RequestBody requestBody);

    @POST("user/saveOrUpdateUserAddress")
    Observable<UpdateAddressResData> saveOrUpdateUserAddress(@Body RequestBody requestBody);

    @POST("user/getUserAddress")
    Observable<MyAddressResData> getUserAddress();

    @POST("user/saveOrUpdateUserBank")
    Observable<UpdateProfileResData> saveOrUpdateUserBank(@Body RequestBody requestBody);

    @POST("user/getUserBank")
    Observable<MyProfileResData> getUserBank();

    @POST("user/getMessageCodeForUpdate")
    Observable<MsgCodeResData> getMessageCodeForUpdate();

    @Multipart
    @POST("user/uploadImg")
    Observable<BaseResData> uploadImg(@Part MultipartBody.Part photo);

    @POST("user/listUserInviter")
    Observable<MyInviterResData> listUserInviter(@Body RequestBody requestBody);

    @POST("product/pageProduct")
    Observable<ProductResData> pageProduct(@Body RequestBody requestBody);

    @POST("product/getBannerProduct")
    Observable<BannerResData> getBannerProduct();

}
