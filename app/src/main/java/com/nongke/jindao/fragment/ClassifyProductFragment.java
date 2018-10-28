package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.ProductAdapter;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ScreenUtils;
import com.nongke.jindao.mcontract.ProductClassifyContract;
import com.nongke.jindao.mpresenter.ProductClassifyPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class ClassifyProductFragment extends BaseMvpFragment<ProductClassifyPresenter> implements ProductClassifyContract.View {

    @BindView(R.id.product_recyclerview)
    RecyclerView product_recyclerview;
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;
    String productName,orderType, orderBy;
    boolean hasNextPage = true;
    private List<Product> articleDataList;
    private ProductAdapter feedArticleAdapter;
    public boolean isSearching=false;

    public static ClassifyProductFragment newInstance(String productName, String orderType, String orderBy) {
        ClassifyProductFragment fragment = new ClassifyProductFragment();
        Bundle args = new Bundle();
        args.putString("productName", productName);
        args.putString("orderType", orderType);
        args.putString("orderBy", orderBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        productName = bundle.getString("productName", "");
        orderType = bundle.getString("orderType", "");
        orderBy = bundle.getString("orderBy", "");
    }

    @Override
    public void initView() {
        initRecyclerView();
        initSmartRefreshLayout();
    }

//    public void searchProduct(String orderType, String orderBy) {
//        if (articleDataList != null) articleDataList.clear();
//        mPresenter.pageNum = 1;
//        product_recyclerview.scrollToPosition(0);
//        mPresenter.pageProduct(Constants.pageSize, orderType, orderBy);
//    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_commodity;
    }

    @Override
    public void reload() {
    }

    @Override
    public ProductClassifyPresenter initPresenter() {
        return new ProductClassifyPresenter();
    }

    @Override
    protected void loadData() {
        if(!Constants.orderBy_product_price.equals(orderBy)&&!isSearching)
        mPresenter.pageProduct("", Constants.orderType_DESC, Constants.orderBy_create_time);
    }

    public void changeOrderBy(String productName,String orderType, String orderBy) {
        this.productName=productName;
        this.orderType=orderType;
        this.orderBy=orderBy;
        if (articleDataList != null) articleDataList.clear();
        mPresenter.pageNum = 1;
        product_recyclerview.scrollToPosition(0);
        mPresenter.pageProduct(productName, orderType, orderBy);
        isSearching=true;
    }
    @Override
    public void showProduct(ProductResData productResData, boolean isRefresh) {
        final List<Product> newDataList = productResData.rspBody.list;
        hasNextPage = productResData.rspBody.hasNextPage;
        if (productResData.rspBody.list.size() == 0) {
            smartRefreshLayout.finishLoadMore();
            return;
        }
        if (!isRefresh) {
//            mAdapter.replaceData(feedProductResData.getDatas());
            smartRefreshLayout.finishRefresh(true);
        } else {
            articleDataList.addAll(newDataList);
            feedArticleAdapter.notifyItemRangeInserted(articleDataList.size() - newDataList.size(), newDataList.size());
            feedArticleAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishLoadMore();
        }

    }

    private void initRecyclerView() {
        articleDataList = new ArrayList<>();
        feedArticleAdapter = new ProductAdapter(getActivity(), articleDataList);
        product_recyclerview.addItemDecoration(new SpacesItemDecoration(2, ScreenUtils.dp2px(getActivity(), 10), false));

        product_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        product_recyclerview.setAdapter(feedArticleAdapter);
    }

    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                LogUtil.d("hasNextPage----------------" + hasNextPage);
                if (hasNextPage)
                    mPresenter.onLoadMore(productName, orderType, orderBy);
                else
                    smartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.onRefreshMore();
            }
        });
    }
}
