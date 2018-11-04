package com.nongke.jindao.mpresenter;


import android.util.Log;

import com.nongke.jindao.R;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.application.CustomApplication;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.CartContract;
import com.nongke.jindao.mcontract.ProductDetailContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class CartPresenter extends BasePresenter<CartContract.View> implements CartContract.Presenter {

    private int pageNum = 1;

    @Override
    public void getCartProduct() {
//        HashMap hashMap = new HashMap();
//        hashMap.put("amount", amount);
//        hashMap.put("productId", productId);
//
//        String jsonString = new JSONObject(hashMap).toString();
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getCartProduct();
        addSubscribe(observable, new BaseObserver<ProductResData>(false) {
            @Override
            public void onNext(ProductResData productResData) {
                mView.showProduct(productResData);
//                LogUtil.d2("getCartProduct.rspBody.list.size()------------ :" + productResData.rspBody.list.size());
//                LogUtil.d2("getCartProduct------------ :" + productResData.toString());
            }
        });
    }

    @Override
    public void updateProductAmount(int amount, int productId) {

        HashMap hashMap = new HashMap();
        hashMap.put("amount", amount);
        hashMap.put("productId", productId);
        String jsonString = new JSONObject(hashMap).toString();
        Log.d("OrderProductPresenter","updateProductAmount-------jsonString："+jsonString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).updateProductAmount(requestBody);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                LogUtil.d2("updateProductAmount------------ :" + baseResData.toString());
            }
        });
    }

    @Override
    public void deleteProduct(int amount, int productId) {
        HashMap hashMap = new HashMap();
        hashMap.put("amount", amount);
        hashMap.put("productId", productId);
        String jsonString = new JSONObject(hashMap).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).deleteProduct(requestBody);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                LogUtil.d2("deleteProduct------------ :" + baseResData.toString());
            }
        });
    }

    @Override
    public void clearCart() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).clearCart();
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                LogUtil.d2("clearCart------------ :" + baseResData.toString());
            }
        });
    }


}
