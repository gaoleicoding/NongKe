package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.ProductClassifyContract;
import com.nongke.jindao.mcontract.ProductContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ProductClassifyPresenter extends BasePresenter<ProductClassifyContract.View> implements ProductClassifyContract.Presenter {
    public int pageNum = 1;

    @Override
    public void pageProduct(String productName, String orderType, String orderBy) {
        LogUtil.d("pageProduct----------------pageNum:"+pageNum);
        LogUtil.d("pageProduct----------------productName:"+productName);
        LogUtil.d("pageProduct----------------orderType:"+orderType);
        LogUtil.d("pageProduct----------------orderBy:"+orderBy);
        getPageProduct( productName, orderType, orderBy);
    }



    @Override
    public void onLoadMore( String productName, String orderType, String orderBy) {
        pageNum=pageNum+1;
        LogUtil.d("onLoadMore----------------pageNum:"+pageNum);
        LogUtil.d("onLoadMore----------------productName:"+productName);
        LogUtil.d("onLoadMore----------------orderType:"+orderType);
        LogUtil.d("onLoadMore----------------orderBy:"+orderBy);

        getPageProduct(productName, orderType, orderBy);

    }

    private void getPageProduct( String productName, String orderType, String orderBy) {
        HashMap hashMap = new HashMap();
        hashMap.put("pageNum", pageNum);
        hashMap.put("pageSize", Constants.pageSize);
        hashMap.put("productName", productName);
        hashMap.put("orderType", orderType);
        hashMap.put("orderBy", orderBy);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).pageProduct(requestBody);
        addSubscribe(observable, new BaseObserver<ProductResData>(false) {
            @Override
            public void onNext(ProductResData productResData) {
                LogUtil.d2("productResData------------ :" + productResData.toString());

                mView.showProduct(productResData, true);

            }
        });
    }
}
