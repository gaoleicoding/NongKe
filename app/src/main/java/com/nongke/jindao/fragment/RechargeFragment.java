package com.nongke.jindao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nongke.jindao.R;
import com.nongke.jindao.activity.ArticleDetailActivity;
import com.nongke.jindao.adapter.DividerItemDecoration;
import com.nongke.jindao.adapter.ProjectListAdapter;
import com.nongke.jindao.adapter.RechargeTabAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mmodel.ProjectListData;
import com.nongke.jindao.mcontract.ProjectContract;
import com.nongke.jindao.mpresenter.ProjectPresenter;
import com.nongke.jindao.view.CustomViewPager;
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
