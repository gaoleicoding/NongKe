package com.nongke.jindao;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.nongke.jindao.adapter.MainTabAdapter;
import com.nongke.jindao.base.activity.BaseActivity;
import com.nongke.jindao.base.utils.PermissionUtil;
import com.nongke.jindao.fragment.HomeFragment;
import com.nongke.jindao.fragment.ClassifyFragment;
import com.nongke.jindao.fragment.CartFragment;
import com.nongke.jindao.fragment.RechargeFragment;
import com.nongke.jindao.fragment.UserFragment;
import com.nongke.jindao.view.CustomViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> titles;

    @BindView(R.id.viewPager)
    public CustomViewPager viewPager;
    @BindView(R.id.tabLayout)
    public TabLayout tabLayout;
    @BindView(R.id.title)
    TextView title;
    HomeFragment homeFragment;
    RechargeFragment projectFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        initView();
        requestPermission();
        String cacheDir=getCacheDir().getPath();
        String filesDir=getFilesDir().getPath();
        Log.d("gaolei","cacheDir---------"+cacheDir);
        Log.d("gaolei","filesDir---------"+filesDir);

    }
    protected void initView(){
        mFragments = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        projectFragment = new RechargeFragment();
        mFragments.add(homeFragment);
        mFragments.add(projectFragment);
        mFragments.add(new ClassifyFragment());
        mFragments.add(new CartFragment());
        mFragments.add(new UserFragment());

        titles = new ArrayList<String>();
        titles.add(getString(R.string.home));
        titles.add(getString(R.string.recharge));
        titles.add(getString(R.string.classfy));
        titles.add(getString(R.string.cart));
        titles.add(getString(R.string.mine));

        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
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

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_home);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_project);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_knowledge);
        tabLayout.getTabAt(3).setCustomView(R.layout.tab_navigation);
        tabLayout.getTabAt(4).setCustomView(R.layout.tab_mine);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(titles.get(tab.getPosition()));
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

    @OnClick({R.id.title})
    public void onClick() {
        int index = viewPager.getCurrentItem();
        if (index == 0)
            homeFragment.scrollToTop();


    }

    public void requestPermission() {
        requestPermission(this, new PermissionUtil.RequestPermissionCallBack() {

            @Override
            public void granted() {

            }

            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE});
    }

    public void onRestart() {
        super.onRestart();
        //跳转到设置界面后返回，重新检查权限
        requestPermission();
    }
}
