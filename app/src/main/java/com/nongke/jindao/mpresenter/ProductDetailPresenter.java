package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.ProductContract;
import com.nongke.jindao.mcontract.ProductDetailContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.View> implements ProductDetailContract.Presenter {

    @Override
    public void saveCart(int amount, int productId) {
        HashMap hashMap = new HashMap();
        hashMap.put("amount", amount);
        hashMap.put("productId", productId);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).saveCart(requestBody);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {
                Utils.showToast(baseResData.retDesc, true);
                LogUtil.d2("saveCart------------ :" + baseResData.toString());


            }
        });
    }
}
