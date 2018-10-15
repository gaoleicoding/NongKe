package com.nongke.jindao.mcontract;

import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.ArticleListData;

/**
 * Created by gaolei on 2018/6/18.
 */

public class HomeContract {

    public interface Presenter {

        /**
         * Get feed article list
         */
        public void getFeedArticleList(int num);

        public void getBannerInfo();

        public void onRefreshMore();

        public void onLoadMore();
    }

    public interface View {

        public void showArticleList(ArticleListData itemBeans, boolean isRefresh);

        public void showBannerList(BannerListData itemBeans);
    }
}
