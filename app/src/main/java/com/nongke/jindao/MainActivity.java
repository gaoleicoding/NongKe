package com.nongke.jindao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.nongke.jindao.activity.RegisterLoginActivity;
import com.nongke.jindao.adapter.MainTabAdapter;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.OnlineParamResData;
import com.nongke.jindao.base.photopicker.ImageUtils;
import com.nongke.jindao.base.photopicker.UriUtils;
import com.nongke.jindao.base.utils.FileProvider7;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.PermissionUtil;
import com.nongke.jindao.base.utils.UserUtil;
import com.nongke.jindao.fragment.CartFragment;
import com.nongke.jindao.fragment.ClassifyFragment;
import com.nongke.jindao.fragment.HomeFragment;
import com.nongke.jindao.fragment.RechargeFragment;
import com.nongke.jindao.fragment.UserFragment;
import com.nongke.jindao.mcontract.OnlineParameContract;
import com.nongke.jindao.mpresenter.OnlineParamePresenter;
import com.nongke.jindao.view.CustomViewPager;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<OnlineParamePresenter> implements OnlineParameContract.View {

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
    ClassifyFragment classifyFragment;
    CartFragment cartFragment;
    UserFragment userFragment;

    private static final int REQUEST_OPEN_CAMERA = 0x011;
    private static final int REQUEST_OPEN_GALLERY = 0x022;
    private static final int REQUEST_CROP_PHOTO = 0x033;
    private static final int REQUEST_PERMISSIONS = 0x044;
    ImageUtils imageUtils;
    private static final String TAG = "photopicker";


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        initView();
        requestPermission();
        String cacheDir = getCacheDir().getPath();
        String filesDir = getFilesDir().getPath();
        Log.d("gaolei", "cacheDir---------" + cacheDir);
        Log.d("gaolei", "filesDir---------" + filesDir);
//        loadGlideImage();
    }

    protected void initView() {
        mFragments = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        projectFragment = new RechargeFragment();
        userFragment = new UserFragment();
        mFragments.add(homeFragment);
        mFragments.add(projectFragment);
        classifyFragment = new ClassifyFragment();
        mFragments.add(classifyFragment);
        cartFragment = new CartFragment();
        mFragments.add(cartFragment);
        mFragments.add(userFragment);

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
//        new PhotoPickerUtil(this);
    }


    /**
     * 设置添加Tab
     */
    private void initTab() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                title.setText(titles.get(position));
                if (position == 3)
                    cartFragment.loadData();
                if (position == 4 && !UserUtil.isLogined()) {
                    RegisterLoginActivity.startActivity(MainActivity.this);
                }


            }

            //标签没选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();

            }
        });
        tabLayout.getTabAt(0).setCustomView(R.layout.tab_home);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_recharge);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_classify);
        tabLayout.getTabAt(3).setCustomView(R.layout.tab_cart);
        tabLayout.getTabAt(4).setCustomView(R.layout.tab_mine);
        //默认选中的Tab
//        tabLayout.getTabAt(0).getCustomView().setSelected(true);
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

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d("PhotoPickerUtil------------------onNewIntent");
        userFragment.refreshUserInfo();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (classifyFragment.defaultFragment.isSearching || classifyFragment.salesFragment.isSearching || classifyFragment.priceFragment.isSearching) {
                classifyFragment.backFromSearch();
                return false;
            }

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtils = userFragment.imageUtils;
        if (resultCode != RESULT_OK) {
            return;
        }
        //data的返回值根据
        switch (requestCode) {
            case REQUEST_OPEN_CAMERA:
                imageUtils.addPicToGallery(imageUtils.imgPathOri);
                imageUtils.cropPhoto(imageUtils.imgUriOri);
                Log.i(TAG, "openCameraResult_imgPathOri:" + imageUtils.imgPathOri);
                Log.i(TAG, "openCameraResult_imgUriOri:" + imageUtils.imgUriOri.toString());
                break;
            case REQUEST_OPEN_GALLERY:
                if (data != null) {
                    Uri imgUriSel = data.getData();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //打开相册会返回一个经过图像选择器安全化的Uri，直接放入裁剪程序会不识别，抛出[暂不支持此类型：华为7.0]
                        //formatUri会返回根据Uri解析出的真实路径
                        String imgPathSel = UriUtils.formatUri(this, imgUriSel);
                        //根据真实路径转成File,然后通过应用程序重新安全化，再放入裁剪程序中才可以识别
                        imageUtils.cropPhoto(FileProvider7.getUriForFile(this, new File(imgPathSel)));
                        Log.i(TAG, "Kit_sel_path:" + imgPathSel);
                        Log.i(TAG, "Kit_sel_uri:" + Uri.fromFile(new File(imgPathSel)));
                    } else {
                        imageUtils.cropPhoto(imgUriSel);
                    }
                    Log.i(TAG, "openGalleryResult_imgUriSel:" + imgUriSel);
                }
                break;
            case REQUEST_CROP_PHOTO:
                imageUtils.addPicToGallery(imageUtils.imgPathCrop);
                ImageUtils.imageViewSetPic(userFragment.iv_user_photo, imageUtils.imgPathCrop);
                revokeUriPermission(imageUtils.imgUriCrop, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                userFragment.uploadPhoto(imageUtils.imgPathCrop);
                Log.i(TAG, "cropPhotoResult_imgPathCrop:" + imageUtils.imgPathCrop);
                Log.i(TAG, "cropPhotoResult_imgUriCrop:" + imageUtils.imgUriCrop.toString());
                break;
        }
    }

    @Override
    public OnlineParamePresenter initPresenter() {
        return new OnlineParamePresenter();
    }

    @Override
    protected void loadData() {
        mPresenter.getOnlineParame();
    }

    @Override
    public void showOnlineParame(OnlineParamResData onlineParamResData) {

    }
}