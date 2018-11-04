package com.nongke.jindao.mpresenter;


import android.util.Log;

import com.google.gson.Gson;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.OrderProduct;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.mcontract.OrderProductContract;
import com.nongke.jindao.mcontract.ProductContract;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class OrderProductPresenter extends BasePresenter<OrderProductContract.View> implements OrderProductContract.Presenter {
    @Override
    public void buyProduct(List<Product> list) {
        HashMap hashMap = new HashMap();
        List productList = new ArrayList();
        for (Product product : list) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.amount = product.amount;
            orderProduct.productId = product.productId;
//            Map map = new HashMap();
//            map.put("amount", product.amount);
//            map.put("productId", product.productId);
            productList.add(orderProduct);
        }

        String listString = "{  \"list\":" + new Gson().toJson(productList) + "}";
//        hashMap.put("list", listString);
//        String jsonString = new JSONObject(hashMap).toString();
        String jsonString = "{\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"amount\": 1,\n" +
                "      \"productId\": 26\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Log.d("OrderProductPresenter", "buyProduct-------jsonString：" + listString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), listString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).buyProduct(requestBody);
        addSubscribe(observable, new BaseObserver<OrderProductResData>(false) {
            @Override
            public void onNext(OrderProductResData productResData) {
                if(productResData.rspBody!=null)
                LogUtil.d2("buyProduct------------ :" + productResData.rspBody.toString());

                mView.showOrderProduct(productResData);

            }
        });
    }
}

