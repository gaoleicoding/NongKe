package com.nongke.jindao.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.activity.ArticleDetailActivity;
import com.nongke.jindao.activity.UserProfileActivity;
import com.nongke.jindao.activity.VipRechargeActivity;
import com.nongke.jindao.adapter.DividerItemDecoration;
import com.nongke.jindao.adapter.ArticleListAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.BannerListData;
import com.nongke.jindao.base.mmodel.ArticleListData;
import com.nongke.jindao.base.mmodel.ArticleListData.FeedArticleData;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.HomeContract;
import com.nongke.jindao.mpresenter.HomePresenter;
import com.nongke.jindao.utils.Constants;
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

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

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
    private List<FeedArticleData> articleDataList;
    private ArticleListAdapter feedArticleAdapter;

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
        mPresenter.getBannerInfo();
//        mPresenter.getFeedArticleList(0);
    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getBannerInfo();
//        mPresenter.getFeedArticleList(0);

    }


    @Override
    public void showArticleList(ArticleListData listData, boolean isRefresh) {
        final List<FeedArticleData> newDataList = listData.data.getDatas();
        if (isRefresh) {
//            mAdapter.replaceData(feedArticleListData.getDatas());
            smartRefreshLayout.finishRefresh(true);
        } else {
            articleDataList.addAll(newDataList);
            feedArticleAdapter.notifyItemRangeInserted(articleDataList.size() - newDataList.size(), newDataList.size());
            feedArticleAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishLoadMore();
        }

        feedArticleAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", articleDataList.get(position).getLink());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showBannerList(BannerListData itemBeans) {

        final List<String> linkList = new ArrayList<String>();
        List imageList = new ArrayList();
        List titleList = new ArrayList();
        int size = itemBeans.data.size();

        for (int i = 0; i < size; i++) {
            imageList.add(itemBeans.data.get(i).getImagePath());
            titleList.add(itemBeans.data.get(i).getTitle());
            linkList.add(itemBeans.data.get(i).getUrl());
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
        banner.setBannerAnimation(Transformer.FlipHorizontal);
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(imageList);//设置图片源
        banner.setBannerTitles(titleList);//设置标题源

        banner.start();


        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", linkList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        articleDataList = new ArrayList<>();
        feedArticleAdapter = new ArticleListAdapter(getActivity(), articleDataList);
        project_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        project_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        project_recyclerview.setAdapter(feedArticleAdapter);
    }

    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
//        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
//                mPresenter.onLoadMore();
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
                MainActivity mainActivity=(MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(1);
                break;
            case R.id.home_vip_layout:
                VipRechargeActivity.startActivity(getActivity());
                break;
            case R.id.home_company_layout:

                break;
            case R.id.home_download_layout:

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
