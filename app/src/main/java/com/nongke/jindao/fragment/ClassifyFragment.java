package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.RechargeTabAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.Constants;
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
    RechargeTabAdapter pagerAdapter;
    boolean isPriceAscend = true;
    private ArrayList<Fragment> mFragments;
    CommodityFragment priceFragment;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView() {
        mFragments = new ArrayList<Fragment>();
        CommodityFragment commodityFragment = new CommodityFragment();
        mFragments.add(CommodityFragment.newInstance(Constants.orderType_DESC, Constants.orderBy_create_time));
        mFragments.add(CommodityFragment.newInstance(Constants.orderType_DESC, Constants.orderBy_sales_amount));
        priceFragment=CommodityFragment.newInstance(Constants.orderType_ASC, Constants.orderBy_product_price);
        mFragments.add(priceFragment);
//        mFragments.add(new CommodityFragment());
        pagerAdapter = new RechargeTabAdapter(getChildFragmentManager(), mFragments);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(pagerAdapter);
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
//        tabLayout.getTabAt(2).getCustomView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                    tabView.setOnClickListener(mTabOnClickListener);
                }
            }
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                title.setText(titles.get(tab.getPosition()));
                int position = tab.getPosition();
                if (position == 2) {

                }
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

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            if (pos == 2) {
                if (isPriceAscend) {
                    ImageView imageView = (ImageView) tabLayout.getTabAt(2).getCustomView().findViewById(R.id.iv_price);
                    imageView.setImageResource(R.drawable.icon_price_descend);
                    isPriceAscend = false;
                    priceFragment.changeOrderBy(Constants.orderType_DESC, Constants.orderBy_product_price);
                } else {
                    ImageView imageView = (ImageView) tabLayout.getTabAt(2).getCustomView().findViewById(R.id.iv_price);
                    imageView.setImageResource(R.drawable.icon_price_ascend);
                    isPriceAscend = true;
                    priceFragment.changeOrderBy(Constants.orderType_ASC, Constants.orderBy_product_price);

                }
            } else {
                TabLayout.Tab tab = tabLayout.getTabAt(pos);
                if (tab != null) {
                    tab.select();
                }
            }
        }
    };


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
