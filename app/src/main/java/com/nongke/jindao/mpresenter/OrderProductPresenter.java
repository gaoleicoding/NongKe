package com.nongke.jindao.mpresenter;


import android.util.Log;

import com.google.gson.Gson;
import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.MyAddressResData;
import com.nongke.jindao.base.mmodel.OrderProduct;
import com.nongke.jindao.base.mmodel.OrderProductResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mmodel.RechargeResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.event.UpdateCartEvent;
import com.nongke.jindao.mcontract.OrderProductContract;
import com.nongke.jindao.mcontract.ProductContract;

import org.greenrobot.eventbus.EventBus;
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
//        String jsonString = "{\n" +
//                "  \"list\": [\n" +
//                "    {\n" +
//                "      \"amount\": 1,\n" +
//                "      \"productId\": 26\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

        Log.d("OrderProductPresenter", "buyProduct-------jsonString：" + listString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), listString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).buyProduct(requestBody);
        addSubscribe(observable, new BaseObserver<OrderProductResData>(false) {
            @Override
            public void onNext(OrderProductResData productResData) {
                if(productResData.rspBody!=null)
                Log.d("OrderActivity","buyProduct------------ :" + productResData.rspBody.toString());

                mView.showOrderProduct(productResData);

            }
        });
    }
    @Override
    public void payForProductOnline(String orderId,int orderType, int payType, String list,float cornMoney,float rmb,
                                    float totalPay,float postage,String uid,String phone,String userName,String address) {
//        orderType (integer, optional): 1:标识vip 购买 2:标识点卡购买 3:标识话费充值 4:标识购物内容 ,
//        payType (integer, optional): 1:标识点卡 2:标识余额(针对点卡) 3:标识支付宝 4:标识微信 ,

        HashMap hashMap = new HashMap();
        hashMap.put("orderId", orderId);
        hashMap.put("orderType", orderType);
        hashMap.put("payType", payType);
//        hashMap.put("list", list);
        hashMap.put("cornMoney", cornMoney);
        hashMap.put("rmb", rmb);
        hashMap.put("totalPay", totalPay);
        hashMap.put("postage", postage);
        hashMap.put("uid", uid);
        hashMap.put("phone", phone);
        hashMap.put("userName", userName);
        hashMap.put("address", address);

        String jsonString = new JSONObject(hashMap).toString();
        StringBuilder sb=new StringBuilder();
        sb.append(jsonString).insert(jsonString.length()-1,",\"list\":"+list);
        Log.d("OrderActivity","jsonString------------ :" +jsonString);
        Log.d("OrderActivity","sb.toString()------------ :" +sb.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sb.toString());
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).payForProductOnline(requestBody);
        addSubscribe(observable, new BaseObserver<RechargeResData>(false) {
            @Override
            public void onNext(RechargeResData rechargeResData) {
                Log.d("OrderActivity","payForProductOnline------------:" +rechargeResData.rspBody.toString());
               mView.showOrderProductPayRes(rechargeResData);

            }
        });
    }
    @Override
    public void getUserAddress() {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getUserAddress();
        addSubscribe(observable, new BaseObserver<MyAddressResData>(false) {
            @Override
            public void onNext(MyAddressResData userAddressResData) {
                Log.d("OrderActivity","getUserAddress------------:" +userAddressResData.toString());

                mView.showUserAddressResData(userAddressResData);
//                if (logoutResData.retDesc.contains("未登录"))
//                    Utils.showToast(CustomApplication.context.getString(R.string.user_not_login), true);
//
            }
        });
    }
}

