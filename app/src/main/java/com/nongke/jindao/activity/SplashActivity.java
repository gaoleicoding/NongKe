package com.nongke.jindao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.event.LoginEvent;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.ResponseStatusUtil;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.account.UserUtil;
import com.nongke.jindao.mcontract.SplashLoginContract;
import com.nongke.jindao.mpresenter.SplashLoginPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends BaseMvpActivity<SplashLoginPresenter> implements SplashLoginContract.View {


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
    }

    @Override
    public SplashLoginPresenter initPresenter() {
        return new SplashLoginPresenter();
    }

    @Override
    protected void loadData() {
        String phone_num = (String) SharedPreferencesUtils.getParam(this, "phone_num", "");
        String password = (String) SharedPreferencesUtils.getParam(this, "password", "");
        if (phone_num.length() > 0 && password.length() > 0) {
            mPresenter.getLoginData(phone_num, password);
        } else handler.sendEmptyMessageDelayed(0, 2000);
    }

    public void jumpToMainActivity(View view) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        handler.removeCallbacksAndMessages(null);
        finish();
    }


    @Override
    public void showLoginResData(LoginResData loginResData) {
        LogUtil.d("loginResData.toString():" + loginResData.toString());
        if ("10000".equals(loginResData.retCode)) {
            UserUtil.setUserInfo(loginResData);
        } else ResponseStatusUtil.handleResponseStatus(loginResData);

        handler.sendEmptyMessageDelayed(0, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent accountEvent) {
        if(UserUtil.isLogined())
        RegisterLoginActivity.startActivity(this);
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
