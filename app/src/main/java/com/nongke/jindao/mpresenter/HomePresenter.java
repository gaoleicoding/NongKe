package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.ArticleListData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.mcontract.HomeContract;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;

import io.reactivex.Observable;



public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private boolean isRefresh = true;
    private int mCurrentPage = 0;

    @Override
    public void onRefreshMore() {
        Observable observable = apiService.getFeedArticleList(-1);
        addSubscribe(observable, new BaseObserver<ArticleListData>(false) {
            @Override
            public void onNext(ArticleListData feedArticleListData) {
                mView.showArticleList(feedArticleListData, true);
            }
        });


    }

    @Override
    public void onLoadMore() {
        ++mCurrentPage;
        Observable observable = apiService.getFeedArticleList(mCurrentPage);
        addSubscribe(observable, new BaseObserver<ArticleListData>(false) {
            @Override
            public void onNext(ArticleListData feedArticleListData) {
                mView.showArticleList(feedArticleListData, false);
            }
        });
    }

    @Override
    public void getFeedArticleList(int num) {
        Observable observable = apiService.getFeedArticleList(num);
        addSubscribe(observable, new BaseObserver<ArticleListData>(true) {
            @Override
            public void onNext(ArticleListData feedArticleListData) {
                mView.showArticleList(feedArticleListData, false);
            }
        });
    }

    @Override
    public void getBannerInfo() {
        Observable observable = apiService.getBannerListData();
        addSubscribe(observable, new BaseObserver<BannerListData>(true) {

            @Override
            public void onNext(BannerListData bannerListData) {
                mView.showBannerList(bannerListData);
            }

        });


    }

}
