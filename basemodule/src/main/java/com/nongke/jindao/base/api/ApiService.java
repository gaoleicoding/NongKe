package com.nongke.jindao.base.api;


import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.ArticleListData;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeReqData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.ProjectListData;
import com.nongke.jindao.base.mmodel.RegisterResData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /*
        http://www.wanandroid.com/project/list/1/json?cid=294
        方法：GET
        参数：cid 分类的id，页码：拼接在链接中，从1开始。
        */
    @GET("project/list/{page}/json")
    Observable<ProjectListData> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    /*
        http://www.wanapiandroid.com/banner/json
        广告栏数据
    */
    @GET("banner/json")
    Observable<BannerListData> getBannerListData();

    /**
     * 获取feed文章列表
     *
     * @param num 页数
     * @return feed文章列表数据
     */
    @GET("article/list/{num}/json")
    Observable<ArticleListData> getFeedArticleList(@Path("num") int num);

    //登录
    @POST("user/login")
    Observable<LoginResData> getLoginData(@Body RequestBody requestBody);

    //注册
    @POST("user/register")
    Observable<RegisterResData> getRegisterData(@Body RequestBody requestBody);

    //获取验证码，type：1注册 2找回手机密码
//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/getMessageCode")
//    @FormUrlEncoded
    Observable<MsgCodeResData> getMessageCode(@Body RequestBody requestBody);

}
