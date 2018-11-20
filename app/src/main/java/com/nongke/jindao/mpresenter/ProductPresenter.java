package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.api.ApiService;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.MessageResData;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.thirdframe.retrofit.RetrofitProvider;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.account.MessageUtil;
import com.nongke.jindao.base.utils.account.OnlineParamUtil;
import com.nongke.jindao.mcontract.ProductContract;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ProductPresenter extends BasePresenter<ProductContract.View> implements ProductContract.Presenter {
    private int pageNum = 1;

    @Override
    public void pageProduct( int pageSize, String orderType, String orderBy) {
        getPageProduct( pageSize, orderType, orderBy);
    }

    @Override
    public void getBannerProduct() {

        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).getBannerProduct();
        addSubscribe(observable, new BaseObserver<BannerResData>(false) {
            @Override
            public void onNext(BannerResData bannerResData) {
                LogUtil.d2("getBannerProduct------------ :" + bannerResData.toString());
                if("10000".equals(bannerResData.retCode)) {
                    mView.showBannerList(bannerResData);
                }
            }
        });
    }

    @Override
    public void onLoadMore( int pageSize, String orderType, String orderBy) {
        LogUtil.d("mCurrentPage----------------"+pageNum);
        ++pageNum;
        getPageProduct(pageSize, orderType, orderBy);
    }

    private void getPageProduct( int pageSize, String orderType, String orderBy) {
        HashMap hashMap = new HashMap();
        hashMap.put("pageNum", pageNum);
        hashMap.put("pageSize", pageSize);
        hashMap.put("orderType", orderType);
        hashMap.put("orderBy", orderBy);

        String jsonString = new JSONObject(hashMap).toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).pageProduct(requestBody);
        addSubscribe(observable, new BaseObserver<ProductResData>(false) {
            @Override
            public void onNext(ProductResData productResData) {
                LogUtil.d2("productResData------------ :" + productResData.toString());
                if("10000".equals(productResData.retCode)) {
                    mView.showProduct(productResData, true);
                }
            }
        });
    }

    @Override
    public void listMessage() {
//        HashMap hashMap = new HashMap();
//        hashMap.put("uid", UserUtil.getUserInfo().rspBody.uid);
//
//        String jsonString = new JSONObject(hashMap).toString();
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitProvider.getInstance().createService(ApiService.class).listMessage();
        addSubscribe(observable, new BaseObserver<MessageResData>(false) {
            @Override
            public void onNext(MessageResData messageResData) {
                LogUtil.d2("listMessage------------ :" + messageResData.toString());
                if("10000".equals(messageResData.retCode)) {
                    mView.showMessageList(messageResData);
                    MessageUtil.setMessageResData(messageResData);
                }
            }
        });
    }
}
