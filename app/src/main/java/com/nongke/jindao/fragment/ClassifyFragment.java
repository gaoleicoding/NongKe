package com.nongke.jindao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongke.jindao.R;
import com.nongke.jindao.adapter.RechargeTabAdapter;
import com.nongke.jindao.base.fragment.BaseMvpFragment;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.base.utils.Constants;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.SoftKeyboardUtil;
import com.nongke.jindao.base.utils.SystemUtil;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class ClassifyFragment extends BaseMvpFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.et_product_search)
    EditText et_product_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    RechargeTabAdapter pagerAdapter;
    boolean isPriceAscend = true;
    private ArrayList<Fragment> mFragments;
    public ClassifyProductFragment defaultFragment, salesFragment, priceFragment;
    String lastSearchName = "";
    String searchName = "";
    int tabPosition = 0;


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        mFragments = new ArrayList<Fragment>();
        ClassifyProductFragment commodityFragment = new ClassifyProductFragment();

        defaultFragment = ClassifyProductFragment.newInstance("", Constants.orderType_DESC, Constants.orderBy_create_time);
        salesFragment = ClassifyProductFragment.newInstance("", Constants.orderType_DESC, Constants.orderBy_sales_amount);
        priceFragment = ClassifyProductFragment.newInstance("", Constants.orderType_ASC, Constants.orderBy_product_price);
        mFragments.add(defaultFragment);
        mFragments.add(salesFragment);
        mFragments.add(priceFragment);
//        mFragments.add(new ClassifyProductFragment());
        pagerAdapter = new RechargeTabAdapter(getChildFragmentManager(), mFragments);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();

        et_product_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    List<View> list = new ArrayList<View>();
                    list.add(et_product_search);
                    SoftKeyboardUtil.hideSoftKeyboard(getActivity(), list);
                    searchProduct();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 设置添加Tab
     */
    private void initTab() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_classify_default);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_classify_sales);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_classify_price);

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

//            if (searchName.equals(et_product_search.getText().toString()))
//                return;

            tabPosition = (int) view.getTag();
            if (!lastSearchName.equals(et_product_search.getText().toString()) && tabPosition == 0) {
                defaultFragment.changeOrderBy(searchName, Constants.orderType_DESC, Constants.orderBy_create_time);
            } else if (!lastSearchName.equals(et_product_search.getText().toString()) && tabPosition == 1) {
                salesFragment.changeOrderBy(searchName, Constants.orderType_DESC, Constants.orderBy_sales_amount);
            } else if (tabPosition == 2) {
                if (isPriceAscend) {
                    ImageView imageView = (ImageView) tabLayout.getTabAt(2).getCustomView().findViewById(R.id.iv_price);
                    imageView.setImageResource(R.drawable.icon_price_descend);
                    isPriceAscend = false;
                    priceFragment.changeOrderBy(searchName, Constants.orderType_DESC, Constants.orderBy_product_price);
                } else {
                    ImageView imageView = (ImageView) tabLayout.getTabAt(2).getCustomView().findViewById(R.id.iv_price);
                    imageView.setImageResource(R.drawable.icon_price_ascend);
                    isPriceAscend = true;
                    priceFragment.changeOrderBy(searchName, Constants.orderType_ASC, Constants.orderBy_product_price);

                }
            } else {
                TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
                if (tab != null) {
                    tab.select();
                }
            }
            lastSearchName = et_product_search.getText().toString();
        }
    };

    @OnClick({R.id.tv_search, R.id.iv_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                searchProduct();
                break;
            case R.id.iv_back:
                backFromSearch();
                break;

        }
    }

    private void searchProduct() {
        searchName = et_product_search.getText().toString();
        if (searchName.length() == 0) {
            Utils.showToast("请输入搜索内容", true);
            return;
        }
        iv_back.setVisibility(View.VISIBLE);
        tabPosition = 0;
        TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
        if (tab != null) {
            tab.select();
        }

        defaultFragment.changeOrderBy(searchName, Constants.orderType_DESC, Constants.orderBy_create_time);
    }

    public void backFromSearch() {
        iv_back.setVisibility(View.GONE);


        et_product_search.setText("");
        searchName = "";
        tabPosition = 0;
        TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
        if (tab != null) {
            tab.select();
        }
        defaultFragment.changeOrderBy("", Constants.orderType_DESC, Constants.orderBy_create_time);
        defaultFragment.isSearching = false;
        salesFragment.isSearching = false;
        priceFragment.isSearching = false;
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
