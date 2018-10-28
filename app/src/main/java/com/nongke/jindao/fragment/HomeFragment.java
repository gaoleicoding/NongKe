package com.nongke.jindao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.ProductDetailActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.activity.WebViewActivity;
import com.nongke.jindao.adapter.ProductAdapter;
import com.nongke.jindao.adapter.divider.DividerItemDecoration;
import com.nongke.jindao.adapter.divider.SpacesItemDecoration;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.BannerResData;
import com.nongke.jindao.base.mmodel.Product;
import com.nongke.jindao.base.mmodel.ProductResData;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ScreenUtils;
import com.nongke.jindao.mcontract.ProductContract;
import com.nongke.jindao.mpresenter.ProductPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class HomeFragment extends BaseMvpFragment<ProductPresenter> implements ProductContract.View {

    @BindView(R.id.project_recyclerview)
    RecyclerView project_recyclerview;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.home_recharge_layout)
    LinearLayout home_recharge_layout;
    @BindView(R.id.home_vip_layout)
    LinearLayout home_vip_layout;
    @BindView(R.id.home_company_layout)
    LinearLayout home_company_layout;
    @BindView(R.id.home_download_layout)
    LinearLayout home_download_layout;
    @BindView(R.id.home_custom_layout)
    LinearLayout home_custom_layout;
    private List<Product> articleDataList;
    private ProductAdapter feedArticleAdapter;
    boolean hasNextPage = true;

//    int pageSize = 6;
//    String orderType = "DESC", orderBy = "create_time";

    @Override
    public void initData(Bundle bundle) {

//        Debug.startMethodTracing("traceview");
//
//        Debug.stopMethodTracing();
    }

    @Override
    public void initView() {
        initSmartRefreshLayout();
        initRecyclerView();
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void reload() {
//        mPresenter.getBannerInfo();
//        mPresenter.getFeedArticleList(0);
    }

    @Override
    public ProductPresenter initPresenter() {
        return new ProductPresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getBannerProduct();

        mPresenter.pageProduct(Constants.pageSize, Constants.orderType_DESC, Constants.orderBy_create_time);

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


    @Override
    public void showBannerList(BannerResData productResData) {

        final List<Product> bannerList = productResData.rspBody;
        LogUtil.d("bannerList.size():" + bannerList.size());
        List imageList = new ArrayList();
        List titleList = new ArrayList();
        final List orderList = new ArrayList();
        int size = bannerList.size();

        for (int i = 0; i < size; i++) {
            imageList.add(bannerList.get(i).img);
            titleList.add(bannerList.get(i).productName);
            orderList.add(bannerList.get(i).productId);
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(getActivity()).load(path).into(imageView);
            }
        });

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        //设置banner动画效果
        //        Tansformer.CubeIn
        //        Transformer.CubeOut
        //        Transformer.DepthPage
        //        Transformer.FlipHorizontal
        //        Transformer.FlipVertical
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(imageList);//设置图片源
        banner.setBannerTitles(titleList);//设置标题源

        banner.start();


        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("product", bannerList.get(position));

                ProductDetailActivity.startActivity(getActivity(), bundle);
            }
        });
    }

    private void initRecyclerView() {
        articleDataList = new ArrayList<>();
        feedArticleAdapter = new ProductAdapter(getActivity(), articleDataList);
        project_recyclerview.addItemDecoration(new SpacesItemDecoration(2, ScreenUtils.dp2px(getActivity(), 10), false));

        project_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        project_recyclerview.setAdapter(feedArticleAdapter);
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
                    mPresenter.onLoadMore(Constants.pageSize, Constants.orderType_DESC, Constants.orderBy_create_time);
                else
                    smartRefreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.onRefreshMore();
            }
        });
    }


    @OnClick({R.id.home_recharge_layout, R.id.home_vip_layout, R.id.home_company_layout, R.id.home_download_layout, R.id.home_custom_layout})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.home_recharge_layout:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(1);
                break;
            case R.id.home_vip_layout:
                VipRechargeActivity.startActivity(getActivity());
                break;
            case R.id.home_company_layout:

                break;
            case R.id.home_download_layout:
                Bundle bundle = new Bundle();
                bundle.putString("fromWhere", Constants.FROM_DOWNLOAD);
                WebViewActivity.startActivity(getActivity(), bundle);
                break;
            case R.id.home_custom_layout:

                break;

            default:
                break;
        }

    }

    public void scrollToTop() {
        project_recyclerview.scrollToPosition(0);
    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {
        super.onDestroy();
    }
}
