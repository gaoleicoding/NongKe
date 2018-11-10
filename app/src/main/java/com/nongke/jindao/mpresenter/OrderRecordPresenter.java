package com.nongke.jindao.mpresenter;


import android.util.Log;

import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.BaseResData;
import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.OrderRecordResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mmodel.UserRecordResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.MessageUtil;
import com.nongke.jindao.mcontract.OrderRecordContract;
import com.nongke.jindao.mcontract.ProductContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class OrderRecordPresenter extends BasePresenter<OrderRecordContract.View> implements OrderRecordContract.Presenter {
    private int pageNum = 1, pageSize = 10;

    @Override
    public void pageUserOrderInfo() {
        getPageProduct();
    }


    @Override
    public void onLoadMore() {
        Log.d("OrderRecordActivity","mCurrentPage----------------" + pageNum);
        ++pageNum;
        getPageProduct();
    }

    @Override
    public void confirmUserOrderInfo(String orderId) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).confirmUserOrderInfo(orderId);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {


            }
        });
    }

    @Override
    public void cancelUserOrderInfo(String orderId) {
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).cancelUserOrderInfo(orderId);
        addSubscribe(observable, new BaseObserver<BaseResData>(false) {
            @Override
            public void onNext(BaseResData baseResData) {


            }
        });
    }

    private void getPageProduct() {
        HashMap hashMap = new HashMap();
        hashMap.put("pageNum", pageNum);
        hashMap.put("pageSize", pageSize);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).pageUserOrderInfo(requestBody);
        addSubscribe(observable, new BaseObserver<OrderRecordResData>(false) {
            @Override
            public void onNext(OrderRecordResData orderRecordResData) {


                mView.showUserOrderInfo(orderRecordResData);

            }
        });
    }

}
