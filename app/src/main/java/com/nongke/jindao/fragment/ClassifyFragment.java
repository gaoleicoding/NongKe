package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.RechargeTabAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.view.CustomViewPager;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class ClassifyFragment extends BaseMvpFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    private ArrayList<Fragment> mFragments;

    @Override
    public void initData( Bundle bundle) {

    }

    @Override
    public void initView() {
        mFragments = new ArrayList<Fragment>();
        CommodityFragment commodityFragment = new CommodityFragment();
        mFragments.add(new CommodityFragment());
        mFragments.add(new CommodityFragment());
        mFragments.add(new CommodityFragment());
//        mFragments.add(new CommodityFragment());
        RechargeTabAdapter adapter = new RechargeTabAdapter(getChildFragmentManager(), mFragments);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();
    }
    /**
     * 设置添加Tab
     */
    private void initTab() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_classify_default);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_classify_sales);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_classify_price);
//        tabLayout.getTabAt(3).setCustomView(R.layout.tab_classify_popularity);



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
    @Override
    public int setContentLayout() {
        return R.layout.fragment_classify;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }
}
