package com.nongke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nongke.R;
import com.nongke.activity.ArticleDetailActivity;
import com.nongke.adapter.DividerItemDecoration;
import com.nongke.adapter.MainTabAdapter;
import com.nongke.adapter.ProjectListAdapter;
import com.nongke.adapter.RechargeTabAdapter;
import com.nongke.base.fragment.BaseMvpFragment;
import com.nongke.base.mmodel.ProjectListData;
import com.nongke.mcontract.ProjectContract;
import com.nongke.mpresenter.ProjectPresenter;
import com.nongke.view.CustomViewPager;
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

public class RechargeFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_recyclerview)
    RecyclerView project_recyclerview;
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    ProjectListAdapter projectAdapter;
    private List<ProjectListData.ProjectData> projectDataList;
    private ArrayList<Fragment> mFragments;

    @Override
    public void initData(Bundle bundle) {


    }

    @Override
    public void initView() {
        initSmartRefreshLayout();
        initRecyclerView();

        mFragments = new ArrayList<Fragment>();
        RechageDetailFragment rechageDetailFragment = new RechageDetailFragment();
        RechageRecordFragment rechageRecordFragment = new RechageRecordFragment();
        mFragments.add(rechageDetailFragment);
        mFragments.add(rechageRecordFragment);
        RechargeTabAdapter adapter = new RechargeTabAdapter(getChildFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_recharge;
    }

    @Override
    public void reload() {
//        mPresenter.getProjectInfo(1, 294);
    }

    @Override
    public ProjectPresenter initPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void loadData() {
//        mPresenter.getProjectInfo(1, 294);
    }


    @Override
    public void showProjectList(ProjectListData listData, boolean isRefresh) {
        final List<ProjectListData.ProjectData> newDataList = listData.data.getDatas();

        if (isRefresh) {
//            mAdapter.replaceData(feedArticleListData.getDatas());
            smartRefreshLayout.finishRefresh(true);
        } else {
            projectDataList.addAll(newDataList);
            projectAdapter.notifyItemRangeInserted(projectDataList.size() - newDataList.size(), newDataList.size());
            projectAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishLoadMore();
        }

        projectAdapter.setOnItemClickListener(new ProjectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", projectDataList.get(position).getLink());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        projectDataList = new ArrayList<>();
        projectAdapter = new ProjectListAdapter(getActivity(), projectDataList);
        project_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        project_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        project_recyclerview.setAdapter(projectAdapter);
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
//                mPresenter.onLoadMore(294);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
//                mPresenter.onRefreshMore(294);
            }
        });
    }
    public void scrollToTop(){
        project_recyclerview.scrollToPosition(0);
    }
    /**
     * 设置添加Tab
     */
    private void initTab() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_recharge_detail);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_recharge_record);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                title.setText(titles.get(tab.getPosition()));
            }

            //标签没选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //默认选中的Tab
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }
}
