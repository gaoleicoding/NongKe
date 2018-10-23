package com.nongke.jindao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.nongke.jindao.MainActivity;
import com.nongke.jindao.R;
import com.nongke.jindao.base.activity.BaseMvpActivity;
import com.nongke.jindao.base.mmodel.LoginResData;
import com.nongke.jindao.base.mmodel.MsgCodeResData;
import com.nongke.jindao.base.mmodel.RegisterResData;
import com.nongke.jindao.base.utils.LogUtil;
import com.nongke.jindao.base.utils.SharedPreferencesUtils;
import com.nongke.jindao.base.utils.UserUtils;
import com.nongke.jindao.base.utils.Utils;
import com.nongke.jindao.mcontract.RegisterLoginContract;
import com.nongke.jindao.mpresenter.RegisterLoginPresenter;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class SplashActivity extends BaseMvpActivity<RegisterLoginPresenter> implements RegisterLoginContract.View {


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

    }

    @Override
    public RegisterLoginPresenter initPresenter() {
        return new RegisterLoginPresenter();
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
    public void showRegisterResData(RegisterResData registerResData) {

    }

    @Override
    public void showResetPasswordData(RegisterResData registerResData) {

    }

    @Override
    public void showLoginResData(LoginResData loginResData) {
        LogUtil.d("loginResData.toString():" + loginResData.toString());
        UserUtils.setUserInfo(loginResData);
//        if ("10000".equals(loginResData.retCode)) {
//            MainActivity.startActivity(this);
//        }
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void showMsgCodeResData(MsgCodeResData msgCodeResData) {

    }
}
